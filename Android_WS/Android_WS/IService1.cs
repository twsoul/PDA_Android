using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Web;
using System.Text;

namespace Android_WS
{
    // 참고: "리팩터링" 메뉴에서 "이름 바꾸기" 명령을 사용하여 코드 및 config 파일에서 인터페이스 이름 "IService1"을 변경할 수 있습니다.

    [ServiceContract]
    public interface IService1
    {

        // TODO: 여기에 서비스 작업을 추가합니다.
       

        /// <summary>
        /// ACS json받기.
        /// </summary>
        /// <param name="SP_NAME"></param>
        /// <param name="PARAMETERS"></param>
        /// <returns></returns>

        [OperationContract]
        [WebInvoke(Method = "POST",
             UriTemplate = "ACS_POST",
            BodyStyle = WebMessageBodyStyle.Wrapped,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        Message ACS_POST(Stream jsonStream);

        [OperationContract]
        [WebInvoke(Method = "POST",
             UriTemplate = "ACS_POST_TEST",
            BodyStyle = WebMessageBodyStyle.Wrapped,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        Message ACS_POST_TEST(Stream jsonStream);
        

        #region 사용안하는 부분190521
        //  [OperationContract]
        //  [WebInvoke(Method = "GET",
        //  UriTemplate = "Get_Login/{USER_ID}/{PASSWORD}",
        //  BodyStyle = WebMessageBodyStyle.Wrapped,
        //  ResponseFormat = WebMessageFormat.Json,
        //  RequestFormat = WebMessageFormat.Json
        //)]
        //  List<WCF_BS> Get_Login(String USER_ID,String PASSWORD);
        //  //WCF_BS Get_Login(WCF_BS p);


        //  [OperationContract]
        //  [WebInvoke(Method = "GET",
        //  UriTemplate = "EMP_SELECT_TABLE/{EMP_ID}/",
        //  BodyStyle = WebMessageBodyStyle.Wrapped,
        //  ResponseFormat = WebMessageFormat.Json,
        //  RequestFormat = WebMessageFormat.Json
        //)]
        //   Message EMP_SELECT_TABLE(String EMP_ID);
        #endregion
    }
    #region 사용안하는 부분190521
    //[DataContract]
    //public class WCF_BS

    //{

    //    [DataMember]

    //    public string USER_ID { get; set; }

    //    [DataMember]

    //    public string PASSWORD { get; set; }
    //    [DataMember]

    //    public string EMP_NO { get; set; }
    //    [DataMember]

    //    public string USER_NM { get; set; }
    //    [DataMember]

    //    public string DEPT_CD { get; set; }
    //    [DataMember]

    //    public string TEL_NO { get; set; }
    //    [DataMember]

    //    public string MOBILE_NO { get; set; }
    //    [DataMember]

    //    public string EMAIL { get; set; }
    //    [DataMember]

    //    public string RETIRE_DATE { get; set; }
    //    [DataMember]

    //    public string REMARK { get; set; }
    //    [DataMember]

    //    public string REG_DATE { get; set; }
    //    [DataMember]

    //    public string REG_ID { get; set; }
    //    [DataMember]

    //    public string login { get; set; }



    //}


    #endregion



}
