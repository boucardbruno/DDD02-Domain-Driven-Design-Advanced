using System.Collections.Generic;

namespace ExternalDependencies
{
    public class CorridorDto(int number, IEnumerable<string> involvedRowNames)
    {
        public int Number { get; set; } = number;
        public IEnumerable<string> InvolvedRowNames { get; set; } = involvedRowNames;
    }
}