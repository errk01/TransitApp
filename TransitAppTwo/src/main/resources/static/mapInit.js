function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: coords,
        zoom: 10,
        scrollwheel: false
    });
    var marker = new google.maps.Marker({
        position: coords,
        map: map,
    });
}

/*<![CDATA[*/
var coords = {
    lat: parseFloat(/*[[${location.lat}]]*/),
    lng: parseFloat(/*[[${location.lng}]]*/),
};
/*]]>*/

var map;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: parseFloat(busLocations[0].LATITUDE), lng: parseFloat(busLocations[0].LONGITUDE) },
        zoom: 15,
        scrollwheel: false
    });

    for (i=0; i<busLocations.length; i++){
        var marker = new google.maps.Marker({
            position: { lat: parseFloat(busLocations[i].LATITUDE), lng: parseFloat(busLocations[i].LONGITUDE) },
            map: map,
        });
    }
}