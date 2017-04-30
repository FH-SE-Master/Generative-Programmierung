using CloningGenerator;
using System;
using Persons;
using Shape;

namespace ClassGenerator {
    class Program
    {
        static void Main(string[] args)
        {
            testPersons();
            testShapes();

            Console.Read();
        }

        public static void testPersons()
        {
            Console.WriteLine("################################################################ \n" 
                            + "testPersons()\n"
                            + "################################################################");

            Department department = new Department()
            {
                Name = "Software engineering"
            };

            Teacher teacher = new Teacher()
            {
                Age = 50,
                Department = department,
                Id = "P00000001",
                Name = "Thomas"
            };

            Student student = new Student() { 
            Id = "S1610454013"
            };

            Console.WriteLine(department + "\n");
            Console.WriteLine(teacher + "\n");
            Console.WriteLine(student + "\n");

            Console.WriteLine("################################################################\n");
        }

        public static void testShapes()
        {
            Console.WriteLine("################################################################ \n"
                              + "testShapes()\n"
                              + "################################################################");

            Coordinate coordinate = new Coordinate();
            coordinate.XCoordinate = 1.0;
            coordinate.YCoordinate = 2.0;

            Rectangular rectangular = new Rectangular();
            rectangular.Height = 1.0;
            rectangular.Width = 2.0;
            rectangular.Name = "Rectangular";
            rectangular.Coordinate = coordinate;
            rectangular.FillColor = "#ffffff";
            rectangular.StrokeHexColor = "#000000";
            rectangular.Stroke = 1;

            Circle circle = new Circle()
            {
                Coordinate = coordinate,
                FillColor = "#ffffff",
                Name = "Circle",
                Radius = 2.0,
                Stroke = 1,
                StrokeHexColor = "#000000"
            };

            Console.WriteLine(coordinate + "\n");
            Console.WriteLine(rectangular + "\n");
            Console.WriteLine(circle + "\n");

            Console.WriteLine("################################################################\n");
        }
    }
}
