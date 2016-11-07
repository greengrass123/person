//判断输入密码的类型  
function CharMode(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写
		return 2;
	if (iN >= 97 && iN <= 122) // 小写
		return 4;
	else
		return 8;
}
// bitTotal函数
// 计算密码模式
function bitTotal(num) {
	modes = 0;
	for (i = 0; i < 4; i++) {
		if (num & 1)
			modes++;
		num >>>= 1;
	}
	return modes;
}
// 返回强度级别
function checkStrong(sPW) {
	if (sPW.length <= 4)
		return 0; // 密码太短
	Modes = 0;
	for (i = 0; i < sPW.length; i++) {
		// 密码模式
		Modes |= CharMode(sPW.charCodeAt(i));
	}
	return bitTotal(Modes);
}

// 显示颜色
function pwStrength(pwd) {
	O_color = "#eeeeee";
	L_color = "#999999";
	M_color = "#FF6633";
	H_color = "#FF3300";
	if (pwd == null || pwd == '') {
		Lcolor = Mcolor = Hcolor = O_color;
	} else {
		S_level = checkStrong(pwd);
		switch (S_level) {
		case 0:
			Lcolor = Mcolor = Hcolor = O_color;
		case 1:
			Lcolor = L_color;
			// Mcolor=Hcolor=O_color;
			break;
		case 2:
			// Lcolor=Mcolor=M_color;
			// Hcolor=O_color;
			Mcolor = M_color;
			break;
		default:
			// Lcolor=Mcolor=Hcolor=H_color;
			Hcolor = H_color;
		}
	}
	document.getElementById("strength_L").style.background = Lcolor;
	document.getElementById("strength_M").style.background = Mcolor;
	document.getElementById("strength_H").style.background = Hcolor;
	return;
}
//检查用户名
function check_user_name() {
	var user = document.getElementById("userName").value;
	if (user.length <= 0) {
		info1.innerHTML = "不能为空";
		return false;
	} else {
		if (user.length > 20) {
			info1.innerHTML = "长度过长";
			return false;
		}
		info1.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}
//检查密码
function check_pw() {
	var pw = document.getElementById("password").value;
	if (pw.length <= 0) {
		info2.innerHTML = "不能为空";
		return false;
	} else {
		if (pw.length < 6) {
			info2.innerHTML = "长度至少为6";
			return false;
		} else
			info2.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}
//检查确认密码
function checkpwagain() {
	var pw = document.getElementById("password").value;
	var pw1 = document.getElementById("repassword").value;
	if (pw != pw1 || pw1.length <= 0) {
		info3.innerHTML = "密码不一致";
		return false;
	} else {
		info3.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}

function check_type() {
	var type = document.getElementById("user_type").value;
	if (type.length <= 0) {
		info4.innerHTML = "不能为空";
		return false;
	} else {
		info4.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}


function check_department() {
	var de = document.getElementById("department").value;
	if (de.length <= 0) {
		info5.innerHTML = "不能为空";
		return false;
	} else {
		info5.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}

//验证号码
function check_num() {
	var num = document.getElementById("phone").value;
	//alert("num.length:"+num.length);
	if(num.length <=0){//可以不填号码信息，填了则应该保证格式正确
		//alert("没有输入号码:"+num.length);
		return true;
	}
	if (num.length!=11) {
		//alert("格式错误:"+num.length);
		info6.innerHTML = "格式错误";
		return false;
	} else {
		if(isNaN(num)){//判断是否为数字，NaN为非数字，是true则不是数字
			info6.innerHTML = "格式错误";
			return false;
		}
		info6.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}
//检查邮件格式
function check_mail() {
	var email = document.getElementById("email").value;
	if (email.length <= 0)//可以不填号码信息，填了则应该保证格式正确
		//alert("没有输入邮箱:"+email.length);
		return true;
	if (email.indexOf('@') <= 0 || email.indexOf('.') < 0
			|| email.indexOf('.') - email.indexOf('@') <= 1
			|| email.substring(email.length - 4) != ".com") {
		//alert("输入邮箱格式错误:"+email.length);
		info7.innerHTML = "格式错误";
		return false;
	} else {
		info7.innerHTML = "<font color='#00FF00' size='+0.7'>√</font>";
	}
	return true;
}

//按提交按钮时检查表单中各参数是否满足条件，不满足则不提交
function check() {
	if (check_user_name() && check_pw() && checkpwagain() && check_num() && check_mail()){
		//alert("true");
		return true;
	}	
	else{
		alert("输入信息不满足条件，请检查");
		return false;
	}		
}