using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLGenerator
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("----------------------------------------------");
            Console.WriteLine("XMLGenerator tests");
            Console.WriteLine("----------------------------------------------");
            Console.WriteLine("testExampleNoValuesAndNoAttributes:");
            Console.WriteLine(testExampleNoValuesAndNoAttributes());
            Console.WriteLine("----------------------------------------------");
            Console.WriteLine("testExampleValuesNoAttributes:");
            Console.WriteLine(testExampleValuesNoAttributes());
            Console.WriteLine("----------------------------------------------");
            Console.WriteLine("testExampleValuesAndAttributes:");
            Console.WriteLine(testExampleValuesAndAttributes());
            Console.WriteLine("----------------------------------------------");
        }

        private static string testExampleNoValuesAndNoAttributes()
        {
            return XmlGenerator.Create()
                .Customer
                .BeginChildElements
                .Name
                .Phone
                .Address.BeginChildElements
                .Street
                .City
                .Zip
                .EndChildElements
                .EndChildElements
                .Generate();
        }

        private static string testExampleValuesNoAttributes()
        {
            return XmlGenerator.Create()
                .Customer.BeginChildElements
                .Name("Franz")
                .Phone("033577021")
                .Address.BeginChildElements
                .Street("Street 5")
                .City("Linz")
                .Zip("4020")
                .EndChildElements
                .EndChildElements
                .Generate();
        }

        private static string testExampleValuesAndAttributes()
        {
            return XmlGenerator.Create()
                .Customer.BeginChildElements
                .Name("Mayr", "FirstName", "Franz")
                .Phone("033577021", "CountryCode", "43")
                .Address.BeginChildElements
                .Street("Street 5")
                .City("Linz")
                .Zip("4020")
                .EndChildElements
                .EndChildElements
                .Generate();
        }
    }
}