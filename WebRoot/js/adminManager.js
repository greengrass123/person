$(function() {
    $(".adminManager_table_radio_allChoice").click(function() {

        var allnoauditChoice = $(".adminManager_table_noaudit_input_checkbox");
        for ( var i = 0; i < allnoauditChoice.length; i++) {
            allnoauditChoice[i].checked = true;
        }
        var allauditChoice=$(".adminManager_table_audit_input_checkbox");
        for ( var i = 0; i < allauditChoice.length; i++) {
            allauditChoice[i].checked = true;
        }

    });
    $(".adminManager_table_radio_inverseChoice").click(function() {

        var inverseNoAuditChoice = $(".adminManager_table_noaudit_input_checkbox");
        for ( var i = 0; i <inverseNoAuditChoice.length; i++) {
            if (inverseNoAuditChoice[i].checked == true) {
                inverseNoAuditChoice[i].checked = false;
            } else {
                inverseNoAuditChoice[i].checked = true;
            }
        }
        var inverseAuditChoice=$(".adminManager_table_audit_input_checkbox");
        for ( var i = 0; i <inverseAuditChoice.length; i++) {
            if (inverseAuditChoice[i].checked == true) {
                inverseAuditChoice[i].checked = false;
            } else {
                inverseAuditChoice[i].checked = true;
            }
        }
    });
   
    $(".adminManager_table_radio_button").click(function(){
       
        var Audit=  $(
        '.adminManager_table_td input[name="audit"]:checked')
        .val();
        
        if (Audit== null) {
            alert("单选框未选中");
        } else {
            //alert("提交表单");
            document.getElementById('form1').submit();
        }
    });
  
    
    
    $(".adminManager_table_a_delete").click(function() {
        if (confirm("Are you sure?")) {

            var noAuditChoice = $(".adminManager_table_noaudit_input_checkbox");
            var auditChoice=$(".adminManager_table_audit_input_checkbox");
            var noAuditAdidArray = $(".adminManager_table_noaudit_hidden_Adid");
            var auditAdidArray = $(".adminManager_table_audit_hidden_Adid");
          
           
            var ADCheckedID = new Array();
            if(noAuditChoice.length!=0){         
                for ( var i = 0; i < noAuditChoice.length; i++) {
                    if (noAuditChoice[i].checked == true) {
                        ADCheckedID.push($.trim(noAuditAdidArray[i].value));
                        alert(noAuditAdidArray[i].value);
                    }
                }
            }
            else{
                for ( var i = 0; i < auditChoice.length; i++) {
                    if (auditChoice[i].checked == true) {
                        ADCheckedID.push($.trim(auditAdidArray[i].value));
                        alert(auditAdidArray[i].value);
                    }
                }
            }
           
            var jsonADCheckedID = JSON.stringify(ADCheckedID);
            var   Audit= $(".indirect_audit").val();
            if(Audit=="已审核"){
              
                $.ajax({
                    type:"get",
                    url : "AdminManagerLogical?info=delAuditBatchInfo&jsonADCheckedID="
                        +jsonADCheckedID,
                     success:function(data){
                         alert("成功");
                     },
                });
            }
            if(Audit=="未审核"){
                
                $.ajax({
                    type:"get",
                    url : "AdminManagerLogical?info=delBatchInfo&jsonADCheckedID="
                        +jsonADCheckedID,
                     success:function(data){
                         alert("成功");
                     },
                });
            }
           

        } else {
            return false;
        }
    });
    
    $(".adminManager_table_a_insert").click(function() {
        if (confirm("Are you sure?")) {

            var noAuditChoice = $(".adminManager_table_noaudit_input_checkbox");
            var auditChoice=$(".adminManager_table_audit_input_checkbox");
            var noAuditAdidArray = $(".adminManager_table_noaudit_hidden_Adid");
            var auditAdidArray = $(".adminManager_table_audit_hidden_Adid");
            var ADCheckedID = new Array();
            if(noAuditChoice.length!=0){         
                for ( var i = 0; i < noAuditChoice.length; i++) {
                    if (noAuditChoice[i].checked == true) {
                        ADCheckedID.push($.trim(noAuditAdidArray[i].value));                      
                    }
                }
            }
            else{
                alert("已经审核过了");
            }
           
            var jsonADCheckedID = JSON.stringify(ADCheckedID);
            $.ajax({
                type:"get",
                url : "AdminManagerLogical?info=batchAuditBy&jsonADCheckedID="
                    +jsonADCheckedID,
                 success:function(data){
                     alert("成功");
                 },
            });

        } else {
            return false;
        }
    });
    
    $(".adminManager_table_mui-switch").click(function(){      //需要这页面没有其他checkbox
        var flag=0;
        if($(" input[type='checkbox']").is(':checked')){
            flag=1;
            $.ajax({
                type:"get",
                url : "AdminManagerLogical?info= TimingProcess&flag="
                    +flag,
                 success:function(data){
                     alert("成功");
                 },
            });
        }else{
            flag=0;
            $.ajax({
                type:"get",
                url : "AdminManagerLogical?info= TimingProcess&flag="
                    +flag,
                 success:function(data){
                     alert("成功");
                 },
            });
        }
        
    });
});
