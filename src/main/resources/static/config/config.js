// 生产静态资源访问
var dev = "http://localhost:8080/scientific/"


$(function () {
    var roleId = sessionStorage.getItem("roleId");
    var data;
    $.ajax({
        type: "GET",
        url: dev + "ScientificUser/getUserRoleAuth",
        async: false,
        data: {},
        success: function (res) {
            data = res.data;
        }
    })
    if (roleId == 1){
        $('#admin').show();
        $("#projectType").show();
        $("#performance").show();
        $("#condition").show();
        $("#project").show();
        $("#projectInfo").show()
        $("#addProject").show()
    }
    data.get(roleId).each(function (index,item) {
        if (item === "projectType"){
            $("#projectInfo").show()
            $("#projectType").show();
        }
        if (item === "performance"){
            $("#performance").show();
        }
        if (item === "condition"){
            $("#projectInfo").show()
            $("#condition").show();
        }
        if (item === "project"){
            $("#project").show();
        }
        if (item === "addProject"){
            $("#addProject").show()
        }
    });

})