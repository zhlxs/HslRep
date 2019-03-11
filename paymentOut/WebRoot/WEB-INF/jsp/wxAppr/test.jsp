<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <title>调查问卷</title>
    <link rel="stylesheet" href="${ctx}/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/test.css">
    <link rel="stylesheet" href="${ctx}/css/style.css">
    <link rel="stylesheet" href="${ctx}/css/input.css">
    <link rel="stylesheet" href="${ctx}/css/jquery-weui.less">
    <link rel="stylesheet" href="${ctx}/css/weui.css">
    <script src="${ctx}/js/jquery-1.8.3.min.js"></script>
    <script src="${ctx}/js/popups.js"></script>
    <script>
        setSize();
        addEventListener('resize',setSize);
        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
        }
    </script>
    <style>
    	.content{
    		text-align:center;
    	}
        body {
            background-color: #D1D7DC;
            font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
        }
    	.goback{
    		position: absolute;
    		left: 15px;
    		top: 20px;
    		width: 40px;
		}
    </style>
</head>
<body>
<div class="wrap">
    <header>
        <span>答题</span>
        <!--<p class="time"><span id="time"></span></p>-->
    </header>
    <div class="subject">
        <!--<p id="title">-->
            <!--<span id="s_type">（单选题）</span>-->
            <!--&lt;!&ndash;<span id="s_content"></span>&ndash;&gt;-->
        <!--</p>-->
        <div class="s_count">题数<span id="s_count">1</span>/<span id="all"></span></div>
        <div class="goback"><img src="img/aback.png" alt="" style="width: 10px;height: 20px;"></div>
    </div>
    <div class="quiz-container">
        <div id="quiz"></div>
        <div class="button" style="display: none">
               <p id="previous">上一题</p>
               <p id="next" style="float: right">下一题</p>
               <p id="submit" style="float: right">提交</p>
         </div>
         <div id="button" style="display: none">
        	<p id="finish">结束</p>
    	 </div>
    </div>
    <div id="results" style="display:none"></div>
    <div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">正在随机生成题目请稍候...</p>
        </div>
    </div>
</div>
<script type="text/javascript">
    //    $(function($) {
    $("#loadingToast").css("display","block");
    var userid,testpaperid,truecount,errorcount,undonecount = 0,stage_questionid,submitanswer,istrue;
    function shownext(obj){
        $(".button").show();
//        console.log($(obj).attr("stage_questionid"));
        stage_questionid = $(obj).attr("stage_questionid");
        submitanswer = $(obj).attr("value");
        if(submitanswer === $(obj).attr("correctAnswer")){
            istrue = 1
        }else{
            istrue = 2
        }
    }
    $.ajax({
        // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/getOfficialExam',
        //url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/getOfficialExam',
        url:'http://localhost:8090/jzwxexam/phone/officialExamController/getOfficialExam',
        type:'post',
        dataType:'json',
        success:function (result) {
            $("#loadingToast").css("display","none");
//            console.log(result);
//                    var d = new Date("1111/1/1,0:" + x + ":0");
//                    interval = setInterval(function() {
//                        var m = d.getMinutes();
//                        var s = d.getSeconds();
//                        m = m < 10 ? "0" + m : m;
//                        s = s < 10 ? "0" + s : s;
//                        time.innerHTML = m + ":" + s;
//                        if (m == 0 && s == 0) {
//                            clearInterval(interval);
//                            return;
//                        }
//                        d.setSeconds(s - 1);
//                    }, 1000);
            if(result.stateType == 1){
                jqalert({
                    title:'提示',
                    content:result.stateMsg,
                    yeslink:'./index_v1.html'
                })
            }else{
                var i = 1;
                var option=new Array();
//            console.log(JSON.parse(result.stateValue));
                var test_info = JSON.parse(result.stateValue);
                userid = test_info.userid;
                testpaperid = test_info.testpaperid;
//            console.log(test_info.stage_questionJson);
                $("#all").text(test_info.stage_questionJson.length);
//            $(".next").click(function () {
//                console.log(111);
//                    $("#title").empty();
                $.each(test_info.stage_questionJson,function (index,value) {
//                console.log(value.optionnumber);
                    if(value.optionnumber == 4){
//                    console.log($("#s_type").text());
//                    $("#s_type").text("（单选题）");
                        var item= {
                            question: value.title,
                            answers: {
                                A: value.options.split('|')[0].split('.')[1],
                                B: value.options.split('|')[1].split('.')[1],
                                C: value.options.split('|')[2].split('.')[1],
                                D: value.options.split('|')[3].split('.')[1]
                            },
                            correctAnswer: value.answer,
                            questionId: value.id,
                            indexOrder: value.title
                        };
                        option.push(item);
                    }else if(value.optionnumber == 3){
//                    console.log($("#s_type").text());
//                    $("#s_type").text("");
//                    $("#s_type").text("（单选题）");
                        var item= {
                            question: value.title,
                            answers: {
                                A: value.options.split('|')[0].split('.')[1],
                                B: value.options.split('|')[1].split('.')[1],
                                C: value.options.split('|')[2].split('.')[1],
//                                        D: value.options.split('|')[3].split('.')[1]
                            },
                            correctAnswer: value.answer,
                            questionId: value.id,
                            indexOrder: value.title
                        };
                        option.push(item);
                    }else if(value.optionnumber == 2){
//                    console.log($("#s_type").text());
//                    $("#s_type").text("（判断题）");
                        var item= {
                            question: value.title,
                            answers: {
                                A: value.options.split('|')[0].split('.')[1],
                                B: value.options.split('|')[1].split('.')[1],
//                                        C: value.options.split('|')[2].split('.')[1],
//                                        D: value.options.split('|')[3].split('.')[1]
                            },
                            correctAnswer: value.answer,
                            questionId: value.id,
                            indexOrder: value.title
                        };
                        option.push(item);
                    }
                });
//            });
//            $(".next").click();
                const myQuestions = option;
                $(function () {
                    $(".button").hide();
                });
                $(".button").css("display","none");
                function buildQuiz() {
                    // we'll need a place to store the HTML output
                    const output = [];

                    // for each question...
                    myQuestions.forEach((currentQuestion, questionNumber) => {
                        // we'll want to store the list of answer choices
                        const answers = [];

                        // and for each available answer...
                        for (letter in currentQuestion.answers) {
                            // ...add an HTML radio button
                            //console.log(letter);
                            answers.push(
                                `<div class="inputGroup">
                                 <input correctAnswer="${currentQuestion.correctAnswer}" stage_questionid="${currentQuestion.questionId}" onclick="shownext(this)" id="${currentQuestion.answers[letter]}" type="radio" name="question${questionNumber}" value="${letter}">
                                 <label for="${currentQuestion.answers[letter]}">
                                 <span style="position: absolute;left: 5px;">${letter} :</span>
                                 <span style="display: inline-block;margin-left: 5px;width: 90%;">${currentQuestion.answers[letter]}</span>
                                 </label>
                                 </div>`
                            );
                        }

                        // add this question and its answers to the output
                        output.push(
                            `<div class="slide">
                            <div class="question"> ${currentQuestion.question} </div>
                            <div class="answers"> ${answers.join("")} </div>
                            </div>`
                        );
                    });

                    // finally combine our output list into one string of HTML and put it on the page
                    quizContainer.innerHTML = output.join("");
                }

                function showResults() {
                    // gather answer containers from our quiz
                    const answerContainers = quizContainer.querySelectorAll(".answers");

                    // keep track of user's answers
                    let numCorrect = 0;

                    // for each question...
                    myQuestions.forEach((currentQuestion, questionNumber) => {
                        // find selected answer
                        const answerContainer = answerContainers[questionNumber];
                        const selector = `input[name=question${questionNumber}]:checked`;
                        const userAnswer = (answerContainer.querySelector(selector) || {}).value;

                        // if answer is correct
                        if (userAnswer === currentQuestion.correctAnswer) {
                            // add to the number of correct answers
                            numCorrect++;

                            // color the answers green
//                        answerContainers[questionNumber].style.color = "lightgreen";
                        } else {
                            // if answer is wrong or blank
                            // color the answers red
//                        answerContainers[questionNumber].style.color = "red";
                        }
                    });

                    // show number of correct answers out of total
                    resultsContainer.innerHTML = `你答对了${myQuestions.length}题中的${numCorrect}题`;
                    $(".button").css("display","none");
                    finishButton.style.display = "block";
                    truecount = numCorrect;
                    errorcount = myQuestions.length - numCorrect;
                    undonecount = 0;
                }

                function showSlide(n) {
                    $(".button").css("display","none");
                    slides[currentSlide].classList.remove("active-slide");
                    slides[n].classList.add("active-slide");
                    currentSlide = n;

                    if (currentSlide === 0) {
                        previousButton.style.display = "none";
                    } else {
                        previousButton.style.display = "inline-block";
                    }

                    if (currentSlide === slides.length - 1) {
                        nextButton.style.display = "none";
                        submitButton.style.display = "inline-block";
                    } else {
                        nextButton.style.display = "inline-block";
                        submitButton.style.display = "none";
                    }
                }

                function showNextSlide() {
//                console.log(111);
                    showSlide(currentSlide + 1);
                    i++;
//                    console.log(i);
                    $("#s_count").text(i);
                    var ajaxData = "userid=" + userid + "&testpaperid=" + testpaperid
                        + "&stage_questionid=" + stage_questionid + "&submitanswer=" + submitanswer
                        + "&istrue=" + istrue;
                    console.log(ajaxData);
                    $.ajax({
                        // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        //url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        //url:'http://127.0.0.1:8002/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        url:'http://localhost:8090/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        type:'post',
                        data:ajaxData,
                        success:function (data) {
//                        console.log(data);
                        }
                    })
                }

                function showPreviousSlide() {
                    showSlide(currentSlide - 1);
                    i--;
                    $("#s_count").text(i)
                }

                const quizContainer = document.getElementById("quiz");
                const resultsContainer = document.getElementById("results");
                const submitButton = document.getElementById("submit");

                // display quiz right away
                buildQuiz();

                const previousButton = document.getElementById("previous");
                const nextButton = document.getElementById("next");
                const slides = document.querySelectorAll(".slide");
//            const options = $("input:radio[name='question0']").val();
                const finishButton = document.getElementById("finish");
                let currentSlide = 0;

                showSlide(0);

                // on submit, show results
                submitButton.addEventListener("click", function () {
//                console.log(111);
//                shownext(this);
                    var ajaxData = "userid=" + userid + "&testpaperid=" + testpaperid
                        + "&stage_questionid=" + stage_questionid + "&submitanswer=" + submitanswer
                        + "&istrue=" + istrue;
                    console.log(ajaxData);
                    $.ajax({
                        // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        //url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        url:'http://localhost:8090/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                        type:'post',
                        data:ajaxData,
                        success:function (data) {
//                        console.log(data);
                        }
                    });
                    jqalert({
                        title:'提示',
                        content:'您确定提交吗？',
                        yestext:'确定提交',
                        notext:'返回检查',
                        yesfn:function () {
                            showResults();
                            var suc_ajaxData = "userid=" + userid + "&testpaperid=" + testpaperid
                                + "&truecount=" + truecount + "&errorcount=" + errorcount
                                + "&undonecount=" + undonecount;
//                        console.log(suc_ajaxData);
                            var sum = truecount+errorcount;
                            $(".weui-toast__content").text("正在提交请稍候...");
                            $("#loadingToast").css("display","block");
                            $.ajax({
                                // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/submitOfficialExamGrade',
                               	//url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/submitAnswerExamGrade',
                                url:'http://localhost:8090/jzwxexam/phone/officialExamController/submitAnswerExamGrade',
                                type:'post',
                                data:suc_ajaxData,
                                success:function (dat) {
                                    $("#loadingToast").css("display","none");
                                    if(dat.stateType == 0){
                                        jqtoast('提交成功');
                                        if (dat.stateValue == 2){
                                            jqalert({
                                                title:'提示',
                                                content:"你答对了"+sum+"题中的"+truecount+"题",
                                                yeslink:'./count.html?userid='+userid + "&testpaperid=" + testpaperid + "&truecount=" + truecount
                                            })
                                        }else{
                                            jqalert({
                                                title:'提示',
                                                content:dat.stateMsg,
                                                yeslink:'./index_v1.html'
                                            })
                                        }
                                    }else{
                                        jqtoast('提交失败，请重试')
                                    }
//                                return true;
                                }
                            });

//                                const answerContainers = quizContainer.querySelectorAll(".answers");
//
//                        // keep track of user's answers
//                                let numCorrect = 0;
//
//                        // for each question...
//                                myQuestions.forEach((currentQuestion, questionNumber) => {
//                        // find selected answer
//                                    const answerContainer = answerContainers[questionNumber];
//                                    const selector = `input[name=question${questionNumber}]:checked`;
//                                    const userAnswer = (answerContainer.querySelector(selector) || {}).value;
//
////                         if answer is correct
//                                    if (userAnswer === currentQuestion.correctAnswer) {
////                                        // add to the number of correct answers
//                                        numCorrect++;
//                                        istrue.push(1);
////                                        // color the answers green
////                                        answerContainers[questionNumber].style.color = "lightgreen";
//                                    } else {
////                                        // if answer is wrong or blank
////                                        // color the answers red
////                                        answerContainers[questionNumber].style.color = "red";
//                                        istrue.push(2);
//                                    }
//                                });

                            // show number of correct answers out of total
//                                resultsContainer.innerHTML = `你答对了${myQuestions.length}题中的${numCorrect}题，得分是${numCorrect}分`;


                        },
                        nofn:function () {
//                        jqtoast('请仔细检查');
                        }
                    });
                });
                finishButton.addEventListener("click",function(){
//                      console.log(111);
//                      shownext(this);
                          var ajaxData = "userid=" + userid + "&testpaperid=" + testpaperid
                              + "&stage_questionid=" + stage_questionid + "&submitanswer=" + submitanswer
                              + "&istrue=" + istrue;
                          console.log(ajaxData);
                          $.ajax({
                              // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                              //url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                              url:'http://localhost:8090/jzwxexam/phone/officialExamController/submitOfficialExamAnswer',
                              type:'post',
                              data:ajaxData,
                              success:function (data) {
//                              console.log(data);
                              }
                          });
                          jqalert({
                              title:'提示',
                              content:'您确定提交吗？',
                              yestext:'确定提交',
                              notext:'返回检查',
                              yesfn:function () {
                                  showResults();
                                  var suc_ajaxData = "userid=" + userid + "&testpaperid=" + testpaperid
                                      + "&truecount=" + truecount + "&errorcount=" + errorcount
                                      + "&undonecount=" + undonecount;
//                              console.log(suc_ajaxData);
                                  var sum = truecount+errorcount;
                                  $(".weui-toast__content").text("正在提交请稍候...");
                                  $("#loadingToast").css("display","block");
                                  $.ajax({
                                      // url:'http://120.78.218.238:8100/jzwxexam/phone/officialExamController/submitOfficialExamGrade',
                                     	//url:'http://127.0.0.1:8001/jzwxexam/phone/officialExamController/submitAnswerExamGrade',
                                      url:'http://localhost:8090/jzwxexam/phone/officialExamController/submitAnswerExamGrade',
                                      type:'post',
                                      data:suc_ajaxData,
                                      success:function (dat) {
                                          $("#loadingToast").css("display","none");
                                          if(dat.stateType == 0){
                                              jqtoast('提交成功');
                                              if (dat.stateValue == 2){
                                                  jqalert({
                                                      title:'提示',
                                                      content:"你答对了"+sum+"题中的"+truecount+"题",
                                                      yeslink:'./count.html?userid='+userid + "&testpaperid=" + testpaperid + "&truecount=" + truecount
                                                  })
                                              }else{
                                                  jqalert({
                                                      title:'提示',
                                                      content:dat.stateMsg,
                                                      yeslink:'./index_v1.html'
                                                  })
                                              }
                                          }else{
                                              jqtoast('提交失败，请重试')
                                          }
//                                      return true;
                                      }
                                  });

//                                      const answerContainers = quizContainer.querySelectorAll(".answers");
      //
//                              // keep track of user's answers
//                                      let numCorrect = 0;
      //
//                              // for each question...
//                                      myQuestions.forEach((currentQuestion, questionNumber) => {
//                              // find selected answer
//                                          const answerContainer = answerContainers[questionNumber];
//                                          const selector = `input[name=question${questionNumber}]:checked`;
//                                          const userAnswer = (answerContainer.querySelector(selector) || {}).value;
      //
////                               if answer is correct
//                                          if (userAnswer === currentQuestion.correctAnswer) {
////                                              // add to the number of correct answers
//                                              numCorrect++;
//                                              istrue.push(1);
////                                              // color the answers green
////                                              answerContainers[questionNumber].style.color = "lightgreen";
//                                          } else {
////                                              // if answer is wrong or blank
////                                              // color the answers red
////                                              answerContainers[questionNumber].style.color = "red";
//                                              istrue.push(2);
//                                          }
//                                      });

                                  // show number of correct answers out of total
//                                      resultsContainer.innerHTML = `你答对了${myQuestions.length}题中的${numCorrect}题，得分是${numCorrect}分`;


                              },
                              nofn:function () {
//                              jqtoast('请仔细检查');
                              }
                          });
                });
                previousButton.addEventListener("click", showPreviousSlide);
                nextButton.addEventListener("click", showNextSlide);
//            console.log(options);
//            for (var i=0;i<=4;i++){
//                console.log($("input")[0]);
//            const options = $("input")[0];
//            const options1 = $("input")[1];
//            const options2 = $("input")[2];
//            const options3 = $("input")[3];
//            options.addEventListener("click", showNextSlide);
//            options1.addEventListener("click", showNextSlide);
//            options2.addEventListener("click", showNextSlide);
//            options3.addEventListener("click", showNextSlide);
//            }
            }
        }
    });

    //console.log(title[0].toString());
    //        console.log(answer);
    //        console.log(answer)

    //    })();
</script>
</body>
</html>