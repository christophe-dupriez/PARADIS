	/*
	 * To display in Bootstrap a collection of max.6 buttons sufficiently spaced but not too much
	 */
	var lastWidth = 0;
	function sizeSpacer(newWidth) {
		lastWidth = newWidth;
		//console.info(newWidth);
	  	var bsContainerWidth = $("body").find('.container').width();
		var spaceWidth = Math.floor(bsContainerWidth / 6) - 44;
		if (spaceWidth < 4) spaceWidth = 4;
		if (spaceWidth > 60) spaceWidth = 60;
		$(".button-spacer").width(spaceWidth);
	}
	sizeSpacer($(window).width());

	$(window).resize(function(){
		var newWidth = $(this).width();
		if(newWidth != lastWidth){
			sizeSpacer(newWidth);
		}		
	});
	
//$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
    $.fn.scrollTo = function( target, options, callback ){
    	  if(typeof options == 'function' && arguments.length == 2){ callback = options; options = target; }
    	  var settings = $.extend({
    	    scrollTarget  : target,
    	    offsetTop     : 50,
    	    duration      : 500,
    	    easing        : 'swing'
    	  }, options);
    	  return this.each(function(){
    	    var scrollPane = $(this);
    	    var scrollTarget = (typeof settings.scrollTarget == "number") ? settings.scrollTarget : $(settings.scrollTarget);
    	    var scrollY = (typeof scrollTarget == "number") ? scrollTarget : scrollTarget.offset().top + scrollPane.scrollTop() - parseInt(settings.offsetTop);
    	    scrollPane.animate({scrollTop : scrollY }, parseInt(settings.duration), settings.easing, function(){
    	      if (typeof callback == 'function') { callback.call(this); }
    	    });
    	  });
    	};

    
    $(document).ready(function(){
			        $('#toBeValidated').bootstrapValidator();
			        $('#loginForm').bootstrapValidator();
			        $("span.totip").tooltip({
			            placement : 'top'
			        });
    });
    
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	    $('#currentTab').val($(e.target).attr("href"));
	    $(e.target).tab('show');
	  });
