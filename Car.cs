using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Google
{
    public class Car
    {
        private string carModel;
        private int carSpeed;

        public Car(string model, int speed)
        {
            this.carModel = model;
            this.carSpeed = speed;
        }

        public string CarModel
        {
            get
            {
                return this.carModel;
            }
        }

        public int CarSpeed
        {
            get
            {
                return this.carSpeed;
            }
            private set { }
        }
    }
}
