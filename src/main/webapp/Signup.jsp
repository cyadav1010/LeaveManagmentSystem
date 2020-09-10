<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Sign Up</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <link href="img/favicon.png" rel="icon">
    <link href="img/apple-touch-icon.png" rel="apple-touch-icon">

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Montserrat:300,400,500,700" rel="stylesheet">

    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/ionicons/css/ionicons.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <link href="css/signup.css" rel="stylesheet">

    <script src="lib/jquery/jquery.min.js"></script>
    <script src="lib/jquery/jquery-migrate.min.js"></script>
    <script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/wow/wow.min.js"></script>
    <script src="lib/waypoints/waypoints.min.js"></script>
    <script src="lib/counterup/counterup.min.js"></script>
    <script src="lib/isotope/isotope.pkgd.min.js"></script>
    <script src="lib/lightbox/js/lightbox.min.js"></script>
    <script src="lib/touchSwipe/jquery.touchSwipe.min.js"></script>
    <script src="js/main.js"></script>
    <script>

        function dis_able(that) {
            if (that.value !== 'manager') {
                document.getElementById("nmanager").style.display = "block";
                document.getElementById("mg").style.display = "block";
            }

            else {
                document.getElementById("nmanager").style.display = "none";
                document.getElementById("mg").style.display = "none";
            }
        }

        function validate(){

            var fname = document.forms["signupform"]["fname"].value;
            var lname = document.forms["signupform"]["lname"].value;
            var emailid = document.forms["signupform"]["emailid"].value;
            var joindate = document.forms["signupform"]["joindate"].value;
            var password = document.forms["signupform"]["password"].value;
            var confirm = document.forms["signupform"]["confirm"].value;

            return (!isEmpty(fname, "First Name")) && (!isEmpty(lname, "Last Name"))
                && (!isEmpty(emailid, "Email ID")) && (!isEmpty(joindate, "Date of Joining"))
                && (!isEmpty(password, "Password")) && (!isEmpty(confirm, "Confirm Password"));
        }

        function isEmpty(elemValue, field){

            if((elemValue === "") || (elemValue == null)){
                alert("Please enter the " + field);
                return true;
            }

            else{
                return false;
            }
        }

        function checkpassword(signupform) {
            if(signupform.password.value !== "" && signupform.password.value === signupform.confirm.value) {
                if(signupform.password.valuelength < 6) {
                    alert("Password must contain at least six characters!");
                    signupform.password.focus();
                    return false;
                }
                re = /[0-9]/;
                if(!re.test(signupform.password.value)) {
                    alert("Password must contain at least one number!");
                    signupform.password.focus();
                    return false;
                }
                re = /[a-z]/;
                if(!re.test(signupform.password.value)) {
                    alert("Password must contain at least one lowercase letter!");
                    signupform.password.focus();
                    return false;
                }
                re = /[A-Z]/;
                if(!re.test(signupform.password.value)) {
                    alert("Password must contain at least one uppercase letter!");
                    signupform.password.focus();
                    return false;
                }
            }

            else if(signupform.password.value !== signupform.confirm.value) {
                alert("Passwords don't match!");
                signupform.password.focus();
                return false;
            }
        }

    </script>

</head>
<body>

<header id="header">
    <div class="container-fluid">

        <div id="logo" class="pull-left">
            <h1><a href="#intro" class="scrollto">CustomerXPs</a></h1>
        </div>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a>Existing Employee?</a></li>
                <li><a href="Home.html">LOGIN</a></li>
            </ul>
        </nav>
    </div>
</header>

<section id="intro">
    <div class="intro-container">
        <div id="introCarousel" class="carousel  slide carousel-fade" data-ride="carousel">
            <div class="carousel-inner" role="listbox">
                <div class="carousel-item active" style="background-image: url('img/intro-carousel/2.jpg');">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2>Sign Up</h2>
                            <form name="signupform" action="signup" method="POST" onsubmit="checkpassword(this);">
                            <div class="row">
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Name : </span>
                                    <input type="text" name="name" id="name" tabindex="1" class="form-control" placeholder="Name" pattern="^[a-zA-Z\s]+" value="">
                                </div>
                                &nbsp; &nbsp;
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Email ID : </span>
                                    <input type="text" name="emailid" id="emailid" tabindex="1" class="form-control" placeholder="Email ID" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" value="">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Date of Joining : </span>
                                    <input type="date" name="joindate" id="joindate" tabindex="2" class="form-control" >
                                </div>
                                &nbsp; &nbsp;
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Department : </span><br/>
                                    <select name="department" class="selectpicker">
                                        <c:forEach items="${departmentsList}" var="dep" >
                                            <option name="managerId"  value="${dep.getDepName()}">
                                                     <c:out value= "${dep.getDepName()}"></c:out>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Role : </span><br/>
                                    <select name="role" class="selectpicker" onChange="dis_able(this)">
                                        <option value="manager">Manager</option>
                                        <option value="regular" selected>Regular User</option>
                                    </select>
                                </div>
                                &nbsp; &nbsp;

                                <div class="col-xs-6 form-group">
                                    <span class="fheading" id="nmanager">Manager Name
                                    <br>
                                    <select name="managerId" id="mg" class="selectpicker">
                                        <c:forEach items="${managersList}" var="managerDetails" >
                                          <option name="managerId"  value="${managerDetails.getManagerId()}">
                                              <!--<c:out value= "${managerDetails.getManagerId()}"></c:out>-->
                                              <c:out value= "${managerDetails.getManagerName()}"></c:out>
                                          </option>
                                        </c:forEach>
                                    </select>
                                        </span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 form-group">
                                    <span class="fheading">Password : </span>
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                </div>
                                &nbsp; &nbsp;
                                <div class="form-group">
                                    <span class="fheading">Re-enter Password : </span>
                                    <input type="password" name="confirm" id="confirm" tabindex="2" class="form-control" placeholder="Confirm Password" onchange="checkpassword(this)">
                                </div>
                            </div>
                            <input type="submit" class="btn-get-started scrollto" name="signupbtn" onclick="validate();">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>
