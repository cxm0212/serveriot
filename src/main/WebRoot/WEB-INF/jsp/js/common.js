
var deviceState={
    OFFLINE : "离线",
    ONLINE : "在线",
    UNACTIVE:"设备未激活",
    DISABLE:"设备已禁用",
    getState : function (value) {
        if(value=="OFFLINE"){
            return deviceState.OFFLINE;
        }else if(value=="ONLINE"){
            return deviceState.ONLINE;
        }else if(value=="UNACTIVE"){
            return deviceState.UNACTIVE;
        }else if(value=="DISABLE"){
            return deviceState.DISABLE;
        }
    }
}

function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}


function dataformatter(value, rec, index) {
    if (value == undefined) {
        return "";
    }
    /*json格式时间转js时间格式*/
    var date = new Date(value);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' +
        date.getDate() + '  ' + date.getHours() + ":" + date.getMinutes();
}

