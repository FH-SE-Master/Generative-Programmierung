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
            String xml = XmlGenerator.Create()
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

            Console.WriteLine(xml);


            xml = XmlGenerator.Create()
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

            Console.WriteLine(xml);
        }
    }
}