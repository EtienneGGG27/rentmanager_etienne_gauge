<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Vehicule
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/cars/modify_?id=${vehicle.idVehicle}">
                            <div class="box-body">
                                <div class="col-sm-10">
                                    <div class="form-group">
                                        <label for="manufacturer" class="col-sm-2 control-label">Marque</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="manufacturer" name="manufacturer" value="${vehicle.constructeur}" required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="modele" class="col-sm-2 control-label">Modele</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="modele" name="modele" value="${vehicle.modele}" required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="seat" class="col-sm-2 control-label">Nombre de places</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="seat" name="seat" value="${vehicle.nb_places}" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
