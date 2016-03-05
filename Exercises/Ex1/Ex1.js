var coordinates = {
    "current": [],
    "bach_khoa": [10.7725465, 106.6587273],
    "phu_tho": [10.7688529, 106.6576841],
    "cau_sai_gon": [10.7988785, 106.7269335]
}

if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
            coordinates.current = [
                position.coords.latitude,
                position.coords.longitude
            ];
        },
        showError);
} else {
    alert("Geolocation is not supported by this browser.");
}

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.");
            break;
        case error.UNKNOWN_ERR:
            alert("An unknown error occurred.");
            break;
    }
}

var main = function() {
    $('.example').click(function() {
        var coordinate;

        switch ($(this).text()) {
            case 'Current location':
                coordinate = coordinates.current;
                break;
            case 'Bach Khoa':
                coordinate = coordinates.bach_khoa;
                break;
            case 'Phu Tho':
                coordinate = coordinates.phu_tho;
                break;
            case 'Cau Sai Gon':
                coordinate = coordinates.cau_sai_gon;
                break;
        }

        $("#lat").val(coordinate[0]);
        $("#lng").val(coordinate[1]);
    });

    $('#btn-search').click(function() {
        var latlng = new google.maps.LatLng($("#lat").val(), $("#lng").val());
        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                var address = JSON.parse(xhttp.responseText).results[0].formatted_address;

                var mapOption = {
                    center: latlng,
                    zoom: 15,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };

                var map = new google.maps.Map(document.getElementById("map"), mapOption);

                var marker = new google.maps.Marker({
                    position: latlng,
                    map: map
                });

                var infoWindow = new google.maps.InfoWindow({
                    content: address,
                    position: latlng,
                    map: map
                });
            }
        };
        xhttp.open("GET", "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latlng.lat() + "," + latlng.lng() + "&sensor=true", true);
        xhttp.send();
    })
};

$(document).ready(main);
