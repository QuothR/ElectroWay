   // Initialize and add the map
   function initMap() {
    // The location of Uluru
    const uluru = { lat: 47.151726, lng: 27.587914 };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map-container"), {
        zoom: 12,
        center: uluru,
    });

    new google.maps.Marker({
        position: { lat: 47.16950582334348, lng: 27.59901572274817},
        map,
        title: "Statie Autobuz",
    });
    new google.maps.Marker({
        position: { lat: 47.156060957329636, lng: 27.604964107236132},
        map,
        title: "EVConnect Statie de incarcare",
    });
    new google.maps.Marker({
        position: { lat: 47.152425497255955, lng: 27.602709621218228},
        map,
        title: "Statie Incarcare Electrica Pentru Masini",
    });
    new google.maps.Marker({
        position: { lat: 47.156352380491704, lng: 27.58697460290722},
        map,
        title: "EVConnect Statie de incarcare",
    });
    new google.maps.Marker({
        position: { lat: 47.16204570930384, lng: 27.57701377501523},
        map,
        title: "Alimentare masini electrice",
    });
    new google.maps.Marker({
        position: { lat: 47.17637452882848, lng: 27.571601436239835},
        map,
        title: "Caby UAIC",
    });
    new google.maps.Marker({
        position: { lat: 47.17793777222658, lng: 27.52133816466764},
        map,
        title: "EVconnect - ETECNIC Charging Station",
    });
    new google.maps.Marker({
        position: { lat: 47.17516215664494, lng: 27.523074666582485},
        map,
        title: "Statie Incarcare Electrica Pentru Masini",
    });
    new google.maps.Marker({
        position: { lat: 47.17695940506633, lng: 27.495303748599873},
        map,
        title: "Statie incarcare electrica BMW",
    });
}