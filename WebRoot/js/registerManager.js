$(function() {
    $(".registerManager_table_radio_allChoice").click(function() {
        var RMcheckbox = $(".registerManager_table_input_checkbox");
        for ( var i = 0; i < RMcheckbox.length; i++) {
            RMcheckbox[i].checked = true;
        }
    });
    $(".registerManager_table_radio_inverse").click(function() {
        var RMcheckbox = $(".registerManager_table_input_checkbox");
        for ( var i = 0; i < RMcheckbox.length; i++) {
            if (RMcheckbox[i].checked == true) {
                RMcheckbox[i].checked = false;
            } else {
                RMcheckbox[i].checked = true;
            }

        }
    });
    $(".registerManager_table_a")
            .click(
                    function() {
                        var RMcheckbox = $(".registerManager_table_input_checkbox");
                        var RMuserID = $(".registerManger_table_td_user");

                        var userCheckedID = new Array();
                        for ( var i = 0; i < RMcheckbox.length; i++) {
                            if (RMcheckbox[i].checked == true) {
                            userCheckedID.push( $.trim(RMuserID[i].innerHTML));
                            }
                        }
                        var jsonCheckedID=JSON
                        .stringify(userCheckedID)
                        $
                                .ajax({
                                    url : "AdminManagerLogical?info=delBatchUser&jsonCheckedID="
                                            + jsonCheckedID,
                                    type : "get",
                                    success : function(data) {
                                        alert("删除成功");
                                    },
                                    error:function(data){
                                        alert("删除失败");
                                    }
                                });
                    });
});