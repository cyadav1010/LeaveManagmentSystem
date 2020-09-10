<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Regular User</title>

    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
    <link href="img/favicon.png" rel="icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link href="img/apple-touch-icon.png" rel="apple-touch-icon">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Montserrat:300,400,500,700" rel="stylesheet">
    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/ionicons/css/ionicons.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="css/regular.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        function display() {
            document.getElementById("show").style.display = 'block';
        }
        function closediv() {
            document.getElementById("show").style.display = 'none';
        }
        function clearNotifications() {
            {"userNotificationsList.clear()"};
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
        .glyphiconbell-custom{
            position:absolute;
            left:40%; /** you can make changes here to change position **/
            top:1px;
            font-size:12px;
            color:white;
            z-index: 3;
        }
    </style>

</head>
<body style="background-image: url('img/intro-carousel/2.jpg');" >
<header id="header">
    <div class="container-fluid">
        <div id="logo" class="pull-left">
            <h1><a href="#intro" class="scrollto">CustomerXPs</a></h1>
        </div>
        <br/><br/>
        <div class="user">
            <h1> ${user.getName()}</h1>
        </div>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
               <li>
                   <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-bell" style="color: limegreen; font-size: 25px;"></i></button>

                   <span class="badge glyphiconbell-custom" style="background-color: white; color: green;">
                        <div id="notificationCount" style="font-size: x-small;"></div>
                    </span>
               </li>
            </ul>
        </nav>
    </div>
</header>

<section id="intro" style="max-height: 400px" >
    <div class="carousel-item active">
        <div class="carousel-container">
            <div class="row" >
                <div class="col-xs-12">
                    <table class="labelprop">
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
                        <tr>
                            <td><label>MANAGER</label></td>
                            <td>:</td>
                            <td><label>${manager}</label></td>
                        </tr>
                    </table>
                </div>

                </div>

        </div>

    </div>
</section>
<div style="position: absolute; margin: 100px ">
    <button type="button" class="btn btn-success" style="margin-right: 50px" data-toggle="modal" data-target="#leave">
        APPLY LEAVE
    </button>
    <div class="modal fade" id="leave" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered"  role="document">
            <div class="modal-content">
                <div class="modal-header" style="margin-top: 100px">
                    <h3 class="modal-title" id="exampleModalLongTitle">LEAVE FORM</h3>
                </div>
                <div class="modal-body">
                    <form action="application" method="post">
                        <div class="form-group">
                            <label for="from-date" class="form-control-label">From:</label>
                            <input type="date" class="form-control" name="from"id="from-date">
                        </div>
                        <div class="form-group">
                            <label for="to-date" class="form-control-label">To:</label>
                            <input type="date" class="form-control" name="to" id="to-date">
                        </div>
                        <div class="form-group">
                            <input type="text" value="${user.getuserId()}" name="userId" hidden>

                        </div>
                        <div class="form-group">
                            <label for="reason" class="form-control-label">Reason:</label>
                            <textarea class="form-control" id="reason" name="reason"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-dismiss="modal">CLOSE</button>
                            <input type="submit" class="btn btn-success" value="SUBMIT">
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>

    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#summary">
        LEAVE LOGS
    </button>
    <div class="modal fade" id="summary" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">LEAVE LOGS</h3>
                </div>
                <div class="modal-body">
                    <h5 class="text-center">Your leave-summary</h5>
                    <table class="table table-striped" id="tblGrid">
                        <thead id="tblHead">
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th class="text-right">Reason</th>
                            <th>No-Of-Days</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${leaveLogs}" var="leave" >

                        <tr><td>${leave.getFromDate()}</td>
                            <td>${leave.getToDate()}</td>
                            <td class="text-right">${leave.getReason()}</td>
                            <td>${leave.getDays()}</td>
                            <td>${StatusName.get(leave.getLeaveStatus())}</td>
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
</div>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
            <button type="button"  data-dismiss="modal"></button>
            <h4 class="modal-title">Notifications</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>