namespace Persons
{
    public abstract class Person
    {
        public string Name { get; set; }
        public int Age { get; set; }

        public override string ToString()
        {
            return "Class: Person \n" + "Name: " + Name + "\n"
                   + "Age: " + Age;
        }
    }

    public class Student : Person
    {
        public string Id { get; set; }

        public override string ToString()
        {
            return base.ToString() + "\n" + "Class: Student \n" + "Id: " + Id;
        }
    }

    public class Teacher : Person
    {
        public string Id { get; set; }
        public Department Department { get; set; }

        public override string ToString()
        {
            return base.ToString() + "\n" + "Class: Teacher \n" + "Id: " + Id + "\n"
                   + "Department: " + Department;
        }
    }

    public class Department : Person
    {
        public string Name { get; set; }

        public override string ToString()
        {
            return base.ToString() + "\n" + "Class: Department \n" + "Name: " + Name;
        }
    }
}

namespace Shape
{
    public class BaseShape
    {
        public string Name { get; set; }
        public Coordinate Coordinate { get; set; }
        public int Stroke { get; set; }
        public string StrokeHexColor { get; set; }
        public string FillColor { get; set; }

        public override string ToString()
        {
            return "Class: BaseShape \n" + "Name: " + Name + "\n"
                   + "Coordinate: " + Coordinate + "\n"
                   + "Stroke: " + Stroke + "\n"
                   + "StrokeHexColor: " + StrokeHexColor + "\n"
                   + "FillColor: " + FillColor;
        }
    }

    public class Rectangular : BaseShape
    {
        public double Width { get; set; }
        public double Height { get; set; }

        public override string ToString()
        {
            return base.ToString() + "\n" + "Class: Rectangular \n" + "Width: " + Width + "\n"
                   + "Height: " + Height;
        }
    }

    public class Circle : BaseShape
    {
        public double Radius { get; set; }

        public override string ToString()
        {
            return base.ToString() + "\n" + "Class: Circle \n" + "Radius: " + Radius;
        }
    }
}


public class Coordinate
{
    public double XCoordinate { get; set; }
    public double YCoordinate { get; set; }

    public override string ToString()
    {
        return "Class: Coordinate \n" + "XCoordinate: " + XCoordinate + "\n"
               + "YCoordinate: " + YCoordinate;
    }
}