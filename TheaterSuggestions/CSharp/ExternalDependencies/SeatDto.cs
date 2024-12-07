namespace ExternalDependencies
{
    public class SeatDto(string name, int category)
    {
        public string Name { get; } = name;
        public int Category { get; } = category;
    }
}