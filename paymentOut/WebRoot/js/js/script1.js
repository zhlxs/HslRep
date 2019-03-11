/**
 * Created by Administrator on 2018/3/13.
 */
//star
//window.onload = function(){
	alert(111);
    var stepW = 30;
    var description = new Array("非常不满意","不满意","一般","满意","非常满意");
    var stars = $(".stars > li");
    var descriptionTemp;
    var option = $(".option");
    $(".showb").css("width",0);
    stars.each(function(i){
        $(stars[i]).click(function(e){
            var n = i+1;
            $(".showb").css({"width":stepW*n});
            descriptionTemp = description[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp;
        });
        // $(stars[i]).click();
    });
    stars.each(function(i){
         console.log(3);
        $(stars[i]).hover(
            function(){
                console.log(1);
                $(".description").text(description[i]);
                option.find(".option-con:eq(" + $(this).index() + ")").show().siblings().hide();
                // $(".prompt").text(description[i]);
            },
            function(){
                console.log(2);
                if(descriptionTemp != null){
                    $(".description").text(descriptionTemp);
                }else{
                    $(".description").text(" ");
                    option.find(".option-con").hide();
                    $(".prompt").show();
                }
            }
        );
    });
//};
function stopDefault(e){
    if(e && e.preventDefault)
        e.preventDefault();
    else
        window.event.returnValue = false;
    return false;
}
