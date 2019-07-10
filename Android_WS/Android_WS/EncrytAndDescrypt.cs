using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Security.Cryptography;
using System.Text;

namespace Android_WS
{
    public static class EncrytAndDescrypt
    {
        static string _key = "acscorp!mescorp!"; // 고정 키값 입력

        public static string Encrypt(string s)
        {

            StringBuilder sbResult = new StringBuilder();

            byte[] KeyArray = UTF8Encoding.UTF8.GetBytes(_key);
            byte[] EncryptArray = UTF8Encoding.UTF8.GetBytes(s);

            RijndaelManaged Rdel = new RijndaelManaged();
            Rdel.Mode = CipherMode.ECB;
            Rdel.Padding = PaddingMode.Zeros;
            Rdel.Key = KeyArray;

            ICryptoTransform CtransForm = Rdel.CreateEncryptor();
            byte[] ResultArray = CtransForm.TransformFinalBlock(EncryptArray, 0, EncryptArray.Length);

            foreach (byte b in ResultArray)
            {
                sbResult.AppendFormat("{0:x2}", b);
            }

            return sbResult.ToString();
        }

        public static string Decrypt(string s)
        {

            byte[] KeyArray = UTF8Encoding.UTF8.GetBytes(_key);
            byte[] EncryptArray = HexToByte(s);

            RijndaelManaged Rdel = new RijndaelManaged();
            Rdel.Mode = CipherMode.ECB;
            Rdel.Padding = PaddingMode.Zeros;
            Rdel.Key = KeyArray;

            ICryptoTransform CtransForm = Rdel.CreateDecryptor();
            byte[] ResultArray = CtransForm.TransformFinalBlock(EncryptArray, 0, EncryptArray.Length);
            return UTF8Encoding.UTF8.GetString(ResultArray);
        }

        private static byte[] HexToByte(string msg)
        {

            msg = msg.Replace(" ", "");
            byte[] comBuffer = new byte[msg.Length / 2];
            for (int i = 0; i < msg.Length; i += 2)
            {
                try
                {
                    comBuffer[i / 2] = (byte)Convert.ToByte(msg.Substring(i, 2), 16);
                }
                catch (ArgumentOutOfRangeException ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
            return comBuffer;
        }





    }
}