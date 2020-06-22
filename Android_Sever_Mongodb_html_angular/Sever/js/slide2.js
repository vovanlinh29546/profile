$(document).ready(function () {
    var show = 1;
    var w = $('#slider1').width() / show;
    var l = $('.slide1').length;
    $('.slide1').width(w);

    $('#slide-container1').width(w * l)
    
    function slider(){
    	$('.slide1').first().animate({
            marginLeft: -w 
        }, 'slow', function () {
            $(this).appendTo($(this).parent()).css({marginLeft: 0});
        });
    }
    var timer = setInterval(slider, 2000);
    
    $('#slider1').hover(function(){
        clearInterval(timer);
    },function(){
        timer = setInterval(slider, 2000);
    });
    
});