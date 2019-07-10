package com.acs.app_update.uCommon.CustomFormat.CustomDate_Format;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView datePickerDialog_Format
 *    Usage        :  datePickerDialog_Format을 Return 시켜준다.
 *                    날짜형식을 짧은 코딩으로 표현하기위해서 Class화 시켜준것일 뿐..
 *
 *                    달력컨트롤을 사용하면 2019-05-07을 클릭 했을 시
 *
 *                    2019-5-7 로 return 되는 현상으로 인해 만듦..
 *
 *  사용목적 : String Result = new datePickerDialog_Format().Format(1994,1,2,"yyyy-MM-dd");
 *              날짜형식을 짧은 코딩으로 표현하기위해서 Class화 시켜준것일 뿐..
 *
 *  사용방법 : String Result = new datePickerDialog_Format().DateTime_Now(); 기본 포맷("yyyy-MM-dd")
 *             String Result = new datePickerDialog_Format().Format(1994,1,2,"yyyy-MM-dd");
 *             String Result = new datePickerDialog_Format().DateTime_Now("yyyy-MM-dd");
 *             형태로 사용하면 된다.
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class datePickerDialog_Format {
    //todo  공통으로 return하는 문자열
    String Return_Date_Format = "";
    Calendar time = Calendar.getInstance();

    //region 입력된 날짜의 포맷을 하여 return하는 기능
    public String Format(int year, int month, int day, String Date_Type) {

        //todo  DateFormat 없을 시 yyyy-MM-dd로 지정
        if (Date_Type == null)
            Date_Type = "yyyy-MM-dd";
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat;
        try {
            dateFormat = new SimpleDateFormat(Date_Type);
        } catch (Exception ex) {
            //todo   에러시 Default 포맷 yyyy-MM-dd
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        Return_Date_Format = dateFormat.format(calendar.getTime());
        return Return_Date_Format;
    }
    //endregion

    //region 오늘 날짜 가져오는 부분 "yyyy-MM-dd"
    public String DateTime_Now() {
        Return_Date_Format = new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());

        return Return_Date_Format;

    }
    //endregion

    //region 오늘 날짜 가져오는 부분 타입별 다름
    public String DateTime_Now(String Date_Type) {

        try {
            Return_Date_Format = new SimpleDateFormat(Date_Type).format(time.getTime());
        } catch (Exception ex) {
            //todo   에러시 Default 포맷 yyyy-MM-dd
            Return_Date_Format = new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());
        }
        return Return_Date_Format;

    }
//endregion
}
