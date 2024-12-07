using System.Collections.Generic;

namespace ExternalDependencies
{
    public class CorridorDto
    {
        public CorridorDto(int number, IEnumerable<string> involvedRowNames)
        {
            Number = number;
            InvolvedRowNames = involvedRowNames;
        }
        public int Number { get; set; }
        public IEnumerable<string> InvolvedRowNames { get; set; }
    }
}