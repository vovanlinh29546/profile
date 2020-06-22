$(document).ready(function () {
    var show = 1;
    var w = $('#slider').width() / show;
    var l = $('.slide').length;
    $('.slide').width(w);

    $('#slide-container').width(w * l)
    
    function slider(){
    	$('.slide').first().animate({
            marginLeft: -w 
        }, 'slow', function () {
            $(this).appendTo($(this).parent()).css({marginLeft: 0});
        });
    }
    var timer = setInterval(slider, 2000);
    
    $('#slider').hover(function(){
        clearInterval(timer);
    },function(){
        timer = setInterval(slider, 2000);
    });
    
});