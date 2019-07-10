using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace Android_WS
{
    public static class Make_MssqlExeption_Return
    {
        public static DataSet Get_Result(SqlException SqlException)
        {
            try
            {
                return Return_DataSet(SqlException);
            }
            catch(Exception ex)
            {
                throw new Exception("데이터를 변환하던 중 에러가 발생하였습니다.");
            }

        }

        public static DataSet Return_DataSet(SqlException SqlException)
        {
            string innerMsg = "";
            if (SqlException.InnerException != null)
            {
                innerMsg = SqlException.InnerException.Message.ToString();
            }
            else
            {
                innerMsg = SqlException.Message.ToString();
            }
            DataSet ds = new DataSet();
            DataTable dt = ds.Tables.Add("Table");

            ds.Tables["Table"].Columns.Add(new DataColumn("err_Result", typeof(string)));
            ds.Tables["Table"].Rows.Add(innerMsg);

            return ds;
        }
    }
}