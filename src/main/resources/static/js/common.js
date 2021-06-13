function setCookie(cname, cvalue, exdays) {
    let d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function appendCookie(cname, cvalue, exdays) {
    let d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    let exCookie = getCookie(cname);
    if (exCookie != "") {
        cvalue = exCookie + "," + cvalue;
    }
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function containsCookie(cname, cvalue) {
    let flag = true;
    let exCookie = getCookie(cname);
    if(exCookie.indexOf(cvalue)==-1){
        flag = false;
    }
    return flag;
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = unescape(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cname, cvalue) {
    let exCookie = getCookie(cname);
    let cvalueLen = cvalue.length;
    let nvalue = "";
    let targetIdx = 0;
    targetIdx = exCookie.indexOf(cvalue);
    if (targetIdx==0) {
        nvalue = exCookie.slice(cvalueLen+1);
    }else{
        nvalue= exCookie.slice(0, targetIdx-1)+exCookie.slice(targetIdx+cvalueLen);
    }
    setCookie(cname, nvalue);
}