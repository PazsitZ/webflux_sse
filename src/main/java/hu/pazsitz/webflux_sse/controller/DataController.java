package hu.pazsitz.webflux_sse.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import hu.pazsitz.webflux_sse.model.MovieJsonModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.BaseStream;

@RestController
@RequestMapping("/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    private static final int INDEX_ID = 6;
    private static final int INDEX_TITLE = 20;
    private static final int INDEX_OR_TITLE = 8;
    private static final int INDEX_REL_DATE = 14;
    private static final int INDEX_RATING = 22;

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/get"
        //,produces = MediaType.APPLICATION_STREAM_JSON_VALUE
            ,produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<MovieJsonModel> getData() {
        Flux<MovieJsonModel> result = fromPath(Paths.get("movies2.csv"))
                .skip(1)
                .onErrorReturn("")
                .filter(StringUtils::isNotBlank)
                .map(this::csvToObject)
                .onErrorContinue((throwable, o) -> {
                    logger.error("Error while processing {}. Cause: {}", o, throwable.getMessage());
                })
                .delayElements(Duration.ofMillis(100))
                .log();

        return result;
    }

    private MovieJsonModel toJsonObject(String s) {
        try {
            return mapper.readValue(s, MovieJsonModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MovieJsonModel csvToObject(String line) {
        String[] columns = line.split("\\|");

        MovieJsonModel model = new MovieJsonModel(
                columns[INDEX_ID],
                columns[INDEX_OR_TITLE],
                columns[INDEX_TITLE],
                columns[INDEX_REL_DATE],
                columns[INDEX_RATING]
        );
        logger.debug(model.toString());
        logger.info(model.toString());
        return model;
    }

    private static Flux<String> fromPath(Path path) {
        return Flux.using(() -> Files.lines(path, Charset.forName(StandardCharsets.UTF_8.name())),
                Flux::fromStream,
                BaseStream::close
        );
    }
}
