using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Configuration;
using System.Data;
using System.Threading;
using System.Security.Cryptography;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Data.SqlClient;
using System.Web.Script.Serialization;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Collections;
using System.ServiceModel.Channels;

namespace Android_WS
{
    // 참고: "리팩터링" 메뉴에서 "이름 바꾸기" 명령을 사용하여 코드, svc 및 config 파일에서 클래스 이름 "Service1"을 변경할 수 있습니다.
    // 참고: 이 서비스를 테스트하기 위해 WCF 테스트 클라이언트를 시작하려면 솔루션 탐색기에서Service1.svc나 Service1.svc.cs를 선택하고 디버깅을 시작하십시오.
    public class Service1 : IService1
    {

        SqlConnection conn;
        SqlCommand sql_cmd;

        string CS = ConfigurationManager.ConnectionStrings["DBConnection"].ConnectionString;
        // OracleConnection ora_conn = new OracleConnection(CS);


        public void DataBaseConnection()
        {
            conn = new SqlConnection(CS);
            sql_cmd = conn.CreateCommand();
        }
        public void DataBaseConnectionDispose()
        {
            conn.Close();
        }

        /// <summary>
        /// SP명과 PARAMETER를 받아오는 기능
        /// 각각 파라미터는 ||로 구분하며
        /// KEY,VALUE는 == 로 구분된다.
        /// 기존 get 방식에서 post방식으로 변경 (암호화 복호화 추가)
        /// </summary>
        /// <param name="SP_NAME"></param>
        /// <param name="PARAMETERS"></param>
        /// <returns></returns>
        public Message ACS_POST(Stream jsonStream)
        {
            StreamReader reader = null;
            string jsonStream_Str = string.Empty;
            String SP_NAME = string.Empty;
            var json_string = string.Empty;
            try
            {
                reader = new StreamReader(jsonStream, Encoding.Default);
                //암호화 된 문장.
                //jsonStream =  acs_parameters=c28e9abc4616ef2b83ca9dca7cd34c293fb7f8f4bf823baf38e1d6822f7c481e5110aba334138a9abbe644afd7cfc3b98a7154f77bb3226ae6a90e6398b5a5ec&
                jsonStream_Str = System.Net.WebUtility.UrlDecode(reader.ReadToEnd()).Trim().Replace("acs_parameters=", "");
                jsonStream_Str = jsonStream_Str.Substring(0, jsonStream_Str.Length - 1);
                jsonStream_Str = EncrytAndDescrypt.Decrypt(jsonStream_Str);
                //jsonStream_Str = "acs_parameters="+jsonStream_Str.Trim();
            

                string[] result_parameters = jsonStream_Str.Split(new string[] { "&nsdv" }, StringSplitOptions.None);
                //parameters = Procedure_Name & nsdpA_WCF_SP_SSPPDA_EMP_L & nsdv@P_BCR_ID & nsdpALL &

                DataBaseConnection();
                conn.Open();

                DataSet ds = new DataSet();
                SqlDataAdapter _sqlDataAdapter;
                string[] Procedure_Name = result_parameters[0].Split(new string[] { "&nsdp" }, StringSplitOptions.None);

                if (!Procedure_Name[1].Contains("dbo."))
                {
                    Procedure_Name[1] = "dbo." + Procedure_Name[1];
                }

                _sqlDataAdapter = new SqlDataAdapter(Procedure_Name[1], conn); // 프로시저명 입력.

                _sqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

                foreach (string result_parameters_value in result_parameters)
                {

                    string[] Result_parameters_detail = result_parameters_value.Split(new string[] { "&nsdp" }, StringSplitOptions.None);
                    if (Result_parameters_detail[0] != "Procedure_Name")
                    {
                        _sqlDataAdapter.SelectCommand.Parameters.AddWithValue(Result_parameters_detail[0], Result_parameters_detail[1].Trim('\0'));

                    }

                }
                try
                {
                    _sqlDataAdapter.Fill(ds);
                }
                catch(SqlException ex)
                {
                    ds=Make_MssqlExeption_Return.Get_Result(ex);
                }
                _sqlDataAdapter.Dispose();

                json_string = JsonConvert.SerializeObject(ds);

                //연결해제
                DataBaseConnectionDispose();

            }
            catch (Exception)
            {

                throw;
            }

            return WebOperationContext.Current.CreateTextResponse(json_string,
                "application/json; charset=utf-8",
                Encoding.UTF8);

        }


        /// <summary>
        /// SP명과 PARAMETER를 받아오는 기능
        /// 각각 파라미터는 ||로 구분하며
        /// KEY,VALUE는 == 로 구분된다.
        /// 기존 get 방식에서 post방식으로 변경 (암호화 복호화 추가)
        /// </summary>
        /// <param name="SP_NAME"></param>
        /// <param name="PARAMETERS"></param>
        /// <returns></returns>
        public Message ACS_POST_TEST(Stream jsonStream)
        {
            StreamReader reader = null;
            string jsonStream_Str = string.Empty;
            String SP_NAME = string.Empty;
            var json_string = string.Empty;
            try
            {
                reader = new StreamReader(jsonStream, Encoding.Default);
                //암호화 된 문장.
                //jsonStream =  acs_parameters=c28e9abc4616ef2b83ca9dca7cd34c293fb7f8f4bf823baf38e1d6822f7c481e5110aba334138a9abbe644afd7cfc3b98a7154f77bb3226ae6a90e6398b5a5ec&
                jsonStream_Str = System.Net.WebUtility.UrlDecode(reader.ReadToEnd()).Trim().Replace("acs_parameters=", "");
                //jsonStream_Str = jsonStream_Str.Substring(0, jsonStream_Str.Length - 1);
                //jsonStream_Str = EncrytAndDescrypt.Decrypt(jsonStream_Str);
                //jsonStream_Str = "acs_parameters="+jsonStream_Str.Trim();


                string[] result_parameters = jsonStream_Str.Split(new string[] { "&nsdv" }, StringSplitOptions.None);
                //parameters = Procedure_Name & nsdpA_WCF_SP_SSPPDA_EMP_L & nsdv@P_BCR_ID & nsdpALL &

                DataBaseConnection();
                conn.Open();

                DataSet ds = new DataSet();
                SqlDataAdapter _sqlDataAdapter;
                string[] Procedure_Name = result_parameters[0].Split(new string[] { "&nsdp" }, StringSplitOptions.None);

                if (!Procedure_Name[1].Contains("dbo."))
                {
                    Procedure_Name[1] = "dbo." + Procedure_Name[1];
                }

                _sqlDataAdapter = new SqlDataAdapter(Procedure_Name[1], conn); // 프로시저명 입력.

                _sqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

                foreach (string result_parameters_value in result_parameters)
                {

                    string[] Result_parameters_detail = result_parameters_value.Split(new string[] { "&nsdp" }, StringSplitOptions.None);
                    if (Result_parameters_detail[0] != "Procedure_Name")
                    {
                        _sqlDataAdapter.SelectCommand.Parameters.AddWithValue(Result_parameters_detail[0], Result_parameters_detail[1].Trim('\0'));

                    }

                }
                try
                {
                    _sqlDataAdapter.Fill(ds);
                }
                catch (SqlException ex)
                {
                    ds = Make_MssqlExeption_Return.Get_Result(ex);
                }
                _sqlDataAdapter.Dispose();

                json_string = JsonConvert.SerializeObject(ds);

                //연결해제
                DataBaseConnectionDispose();

            }
            catch (Exception)
            {

                throw;
            }

            return WebOperationContext.Current.CreateTextResponse(json_string,
                "application/json; charset=utf-8",
                Encoding.UTF8);

        }

    }
}



