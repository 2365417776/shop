window.onload = function(){
    // 商品详情与评价
    $(function(){
        $("div.productReviewDiv").hide();
        $("a.productDetailTopReviewLink").click(function(){
            $("div.productReviewDiv").show();
            $("div.productDetailDiv").hide();
        });
        $("a.productReviewTopPartSelectedLink").click(function(){
            $("div.productReviewDiv").hide();
            $("div.productDetailDiv").show();
        });
    })

    // 确认订单信息
    $(function(){
        $("img.leaveMessageImg").click(function(){
            $(this).hide();
            $("span.leaveMessageTextareaSpan").show();
            $("div.orderItemSumDiv").css("height","100px");
        });
    });
    // 注册用户
    $(function(){
        $email = $("#email_select");
        $phone = $("#phone_select");
        $email_register = $("#email_register");
        $phone_register = $("#phone_register");
        $phone_register.animate({right:"-300px"},0);
        $phone_register.hide();
        $email.on("click", function(){
            $email.css({
                cursor:"default",
                borderBottom:"1px solid #4741ff"
            });
            $email_register.show();
            $email_register.animate({left:0},300);
            $phone_register.hide();
            $phone_register.animate({right:"-300px"},0);
            $phone.css({
                borderBottom:"none"
            });
            $email.hover(function(){
                $email.css({
                    cursor:"default"
                });
            });
            $phone.hover(function(){
                $phone.css({
                    cursor:"pointer"
                });
            })


        });

        $phone.on("click",function(){
            $phone.css({
                cursor:"default",
                borderBottom:"1px solid #4741ff"
            });
            $email_register.animate({left:"-300px"},0);
            $email_register.hide();
            $phone_register.show();
            $phone_register.animate({right:0},300);
            $email.css({
                borderBottom:"none"
            });
            $email.hover(function(){
                $email.css({
                    cursor:"pointer"
                });
            });
            $phone.hover(function(){
                $phone.css({
                    cursor:"default"
                });
            })
        });
    })

};