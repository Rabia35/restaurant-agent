using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Bodego
    {
        protected Texture2D texture;

        public Texture2D caja;

        public Vector2 position { get; set; }

        public const int BodegoH = 64;
        public const int BodegoW = 64;
        public string ingrediente;

        public Bodego(Texture2D theTexture, Texture2D caja)
        {
                texture = theTexture;
                this.caja = caja;
        }

        public void setPosition(Vector2 pos) 
        {
            position = pos;
        }
        public Vector2 getPosition() 
        {
            return position;
        }
        public Texture2D getImage()
        {
            return texture;
        }
    }
}
