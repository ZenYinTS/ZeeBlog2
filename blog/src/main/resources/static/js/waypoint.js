var waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function(direction) {
        if (direction == 'down') {
            $("#toTop-button").show(500);
        } else {
            $("#toTop-button").hide(500);
        }
    }
})
$('#toTop-button').click(function() {
    $(window).scrollTo(0, 800);
});