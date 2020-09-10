<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <title>Manager</title>

    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
    <link href="img/favicon.png" rel="icon">
    <link href="img/apple-touch-icon.png" rel="apple-touch-icon">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Montserrat:300,400,500,700" rel="stylesheet">
    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/ionicons/css/ionicons.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="css/manager.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet"/>

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
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script type="text/javascript">
        function display() {
            document.getElementById("show").style.display = 'block';
        }
        function closediv() {
            document.getElementById("show").style.display = 'none';
        }
        function showtable() {
            document.getElementById("leavetable").style.display = 'block';
        }
        function closetable() {
            document.getElementById("leavetable").style.display = 'none';
        }
    </script>
    <script>
        var result;
        function ajaxcall() {
            $.ajax({

                url : 'login',
                success : function(responseText) {
                    $('#notificationCount').text(responseText);
                }
            });
        }
        setInterval(function(){ajaxcall()}, 50 );
    </script>

    <style>
        #logoutlink {
            background:none!important;
            border:none;
            padding:0!important;
            font-family:"Montserrat", sans-serif;
            cursor:pointer;
            color: white;
            font-weight: bold;
            font-size: small;
        }
        #header #logoutlink:hover{
            color: #18d26e;
        }
    </style>
</head>
<body style="background-image: url('img/intro-carousel/2.jpg');" >
<%
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache");
    int userName = Integer.parseInt(session.getAttribute("sessionUserId").toString());
    if (userName==0) {
        request.setAttribute("Error", "Session has ended.  Please login.");
        RequestDispatcher rd = request.getRequestDispatcher("Home.html");
        rd.forward(request, response);
    }
%>
<header id="header">
    <div class="container-fluid">
        <div id="logo" class="pull-left">
            <h1><a class="scrollto">CustomerXPs</a></h1>
        </div>
        <br/><br/>
        <div class="user">
            <h1>${user.getName()}</h1>
        </div>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li>
                    <button type="button" class="icon-button" style="margin-right: 40px;"  data-toggle="modal" data-target="#aprrej">
                        <i class="glyphicon glyphicon-bell" style="color: limegreen; font-size: 25px;"></i>
                    </button>
                    <div class="modal fade" id="notificationtoggle" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h3 class="modal-title" style="margin-top: 40px;">LEAVES APPLIED</h3>
                                </div>
                                <div class="modal-body">
                                    <table class="table table-striped" id="tblGrid2" style="width: auto;">
                                        <thead id="tblHead2">
                                        <tr>
                                            <th>Name</th>
                                            <th>From Date</th>
                                            <th>To Date</th>
                                            <th class="text-right">Reason</th>
                                            <th colspan="2" style="text-align: center;">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${appliedLeaves}" var="leave" >
                                            <form action="approveRejectServlet" method="POST">
                                                <tr>
                                                    <input name="user-id" hidden value="${leave.getUserId()}">
                                                    <td><input name="user-name" hidden value="${leave.getUserName()}"> ${leave.getUserName()}</td>
                                                    <td><input name="from" hidden value="${leave.getFromDate()}">${leave.getFromDate()}</td>
                                                    <td><input name="to" hidden value="${leave.getToDate()}">${leave.getToDate()}</td>
                                                    <td><input name="reason" hidden class="text-right" value="${leave.getReason()}">${leave.getReason()}</td>
                                                    <td ><input type="submit" class="btn btn-outline-success" name="approve" value="Approve"> </td>
                                                    <td><input type="submit"  class="btn btn-outline-success" name="reject" value="Reject"> </td>
                                                </tr>
                                            </form>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success">CLEAR NOTIFICATIONS</button>
                                    <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <span class="badge glyphiconbell-custom" style="background-color: white; color: green;">
                        <div id="notificationCount" style="font-size: x-small;"></div>
                    </span>
                </li>
                <li>
                    <form action="logout" method="POST">
                        <button type="submit" id="logoutlink" value="Logout">LOGOUT</button>
                    </form>
                </li>
            </ul>
        </nav>
    </div>
</header>

<section id="intro" style="max-height: 400px" >
    <div class="carousel-item active">
        <div class="carousel-container">
            <div class="row">
                <div class="col-xs-12">
                    <table id="table1" class="labelprop">
                        <tr>
                            <td><label>DEPARTMENT</label></td>
                            <td>:</td>
                            <td><label>${user.getDepartment()}</label></td>
                        </tr>
                        <tr>
                            <td><label>EMAIL</label></td>
                            <td>:</td>
                            <td><label>${user.getEmail()}</label></td>
                        </tr>
                        <tr>
                            <td><label>DATE OF JOINING</label></td>
                            <td>:</td>
                            <td><label>${user.getJoinDate()}</label></td>
                        </tr>
                        <tr>
                            <td><label>ROLE</label></td>
                            <td>:</td>
                            <td><label>${user.getRole()}</label></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<div style="position: absolute; margin: 100px ">

    <button type="button" class="btn btn-success" style="margin-right: 40px;" data-toggle="modal" data-target="#leave">
        APPLY LEAVE
    </button>
    <div class="modal fade" id="leave" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document" style="margin-top: 40px;">
            <div class="modal-content">
                <div class="modal-header" style="margin-top: 100px">
                    <h3 class="modal-title" id="exampleModalLongTitle">LEAVE FORM</h3>
                </div>
                <div class="modal-body">
                    <form action="application" method="post">
                        <div class="form-group">
                            <label for="from-date" class="form-control-label">From :</label>
                            <input type="date" class="form-control" name="from"id="from-date">
                        </div>
                        <div class="form-group">
                            <label for="to-date" class="form-control-label">To :</label>
                            <input type="date" class="form-control" name="to" id="to-date">
                        </div>
                        <div class="form-group">

                        </div>
                        <div class="form-group">
                            <label for="reason" class="form-control-label">Reason :</label>
                            <textarea class="form-control" id="reason" name="reason"></textarea>
                        </div>
                        <input type="text" value="${user.getuserId()}" name="userId" hidden>
                        <%--<input type="text" value="${user.getManager}" name="manager" hidden>--%>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-success" value="SUBMIT">
                            <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-success" style="margin-right: 40px;"  data-toggle="modal" data-target="#aprrej">
        APPROVE/REJECT LEAVES
    </button>
    <div class="modal fade" id="aprrej" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document" style="margin-top: 150px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" style="margin-top: 40px;">LEAVES APPLIED</h3>
                </div>
                <div class="modal-body">
                    <table class="table table-striped" id="tblGrid1">
                        <thead id="tblHead1">
                        <tr>
                            <th hidden></th>
                            <th>Name</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Days</th>
                            <th class="text-right">Reason</th>
                            <th colspan="2" style="text-align: center;">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${appliedLeaves}" var="leave" >
                            <form action="approveRejectServlet" method="POST">
                                <input type="text" value="${user.getuserId()}" name="userId" hidden>

                                <tr>
                                    <input name="user-id" hidden value="${leave.getUserId()}">
                                    <td><input name="user-name" hidden value="${leave.getUserName()}"> ${leave.getUserName()}</td>
                                    <td><input name="from" hidden value="${leave.getFromDate()}">${leave.getFromDate()}</td>
                                    <td><input name="to" hidden value="${leave.getToDate()}">${leave.getToDate()}</td>
                                    <td> <input name="no_of_days" hidden value=${leave.getDays()}>${leave.getDays()}</td>
                                    <td><input name="reason" hidden class="text-right" value="${leave.getReason()}">${leave.getReason()}</td>
                                    <td><input type="submit" class="btn btn-outline-success" name="approve" value="Approve"> </td>
                                    <td><input type="submit"  class="btn btn-outline-success" name="reject" value="Reject"> </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div style="margin-top: 100px;" style="margin-top: 100px;" sty>
    </div>

    <button type="button" class="btn btn-success" style="margin-right: 40px;"  data-toggle="modal" data-target="#summary">
        SUMMARY
    </button>
    <div class="modal fade" id="summary" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document" style="margin-top: 200px">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Summary</h3>
                </div>
                <div class="modal-body">
                    <h5 class="text-center">Your leave-summary</h5>
                    <table class="table table-striped" id="tblGrid">
                        <thead id="tblHead">
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th class="text-right">Reason</th>
                            <th>Days</th>
                            <th>Status</th>
                            <th>Approved By</th>
                            <th>Approved On</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${leaveLogs}" var="leave" >

                            <tr><td>${leave.getFromDate()}</td>
                                <td>${leave.getToDate()}</td>
                                <td class="text-right">${leave.getReason()}</td>
                                <td>${leave.getDays()}</td>
                                <td>${StatusName.get(leave.getLeaveStatus())}</td>
                                <td>${leave.getApprovedBy()}</td>
                                <td>${leave.getApprovedOn()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-success" style="margin-right: 40px;"  data-toggle="modal" data-target="#sub">
        SUBORDINATES
    </button>
    <div class="modal fade" id="sub" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document" style="margin-top: 100px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">SUBORDINATES</h3>
                </div>
                <div class="modal-body">
                    <h5 class="text-center">Employees working under you</h5>
                    <table class="table table-striped" id="tblGrid3">
                        <thead id="tblHead3">
                        <tr>
                            <th>Name</th>
                            <th>Department</th>
                            <th>Joining-Date</th>
                            <th>Leaves-Left</th>
                            <th>Leaves-Taken</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${subordinates}" var="userDetails" >
                            <tr>
                                <td>${userDetails.getName()}</td>
                                <td>${userDetails.getDepartment()}</td>
                                <td>${userDetails.getJoinDate()}</td>
                                <td>${userDetails.getTotalleaves()}</td>
                                <td>${userDetails.getLeavesTaken()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-success" style="margin-right: 40px;"  data-toggle="modal" data-target="#upload">
        UPLOAD
    </button>
    <div class="modal fade" id="upload" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document" style="margin-top: 50px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">UPLOAD EMPLOYEE DETAILS</h3>
                </div>
                <div class="modal-body">
                    <form action="upload" method="post" enctype="multipart/form-data">
                        <p style="text-align: center;">Select a .csv file (only)</p>
                        <center><input type="file" name="csvfile" accept=".csv"><br></center>
                        <button type="button" class="btn btn-success" value="Submit" style="float: right;">UPLOAD</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>