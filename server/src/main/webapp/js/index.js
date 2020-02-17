$('body').ready(function () {
    $("#start").datepicker({
        language:"zh-CN",
        todayHighlight: true
    });
    $("#end").datepicker({
        language:"zh-CN",
        todayHighlight: true
    });
    var date = new Date();
    $('#start').val(formatDate(date));
    date.setFullYear(date.getFullYear()+5);
    $('#end').val(formatDate(date));
    function validForm(){
        var reg = /([\d,[A-Z]){32}/;
        var md5 = $('#md5').val();
        if(!reg.test(md5)){
            alert("机器码无效");
            return false;
        }
        var start = $('#start').val();
        var end = $('#end').val();
        if(start === ''||end === ''){
            alert("时间不能为空");
            return false;
        }
        return true;
    }
    $('#submit').click(function () {
        if(validForm()){
            var request = new XMLHttpRequest();
            request.open('post','/license/form/generateLicense');
            request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            request.responseType = 'blob';
            request.onreadystatechange = function () {
                if(request.readyState === 4 && request.status === 200){
                    var name = request.getResponseHeader("Content-disposition");
                    var filename = name.substring(20,name.length);
                    var blob = new Blob([request.response]);
                    var csvUrl = URL.createObjectURL(blob);
                    var link = document.createElement('a');
                    link.href = csvUrl;
                    link.download = filename;
                    link.click();
                }
            };
            request.send("md5="+$('#md5').val()+'&start='+$('#start').val()+'&end='+$('#end').val());
        }
    });
});
function formatDate(date) {
    return date.getFullYear()+'-'+(date.getMonth()<9 ? ('0'+(date.getMonth()+1)):(date.getMonth()+1))+'-'+(date.getDate()<10 ? ('0'+date.getDate()):date.getDate());
}