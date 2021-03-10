function loadContent() {
    this.source = null;
    this.start = function() {
        var table = document.getElementById("table");

        this.source = new EventSource("http://localhost:8008/data/get");
        this.source.addEventListener("message", function(event) {
            var json = JSON.parse(event.data);
            console.log(json);
            // TODO load content to DOM
            var row = table.getElementsByTagName("tbody")[0]
                .insertRow(-1);

            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);

            cell1.innerHTML = "<a href=\"https://www.imdb.com/title/" + json.imdb_id + "\" target=\"_blank\">link</a>";
            cell2.innerHTML = json.original_title;
            cell3.innerHTML = json.title;
            cell4.innerHTML = json.release_date;
            cell5.innerHTML = json.vote_average;
        });

        this.source.onerror = function(){
            this.close();
        }
    };

    this.stop = function() {
        this.source.close();
    }
}

loadData = new loadContent();


window.onload = function() {
    loadData.start();
}
window.onbeforeunload = function() {
    loadData.stop();
}