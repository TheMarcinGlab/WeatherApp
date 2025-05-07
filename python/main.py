from fastapi import FastAPI, Body
from pydantic import BaseModel
from typing import List
from fpdf import FPDF

app = FastAPI()

class WeatherSnapshot(BaseModel):
    recordedAt: str
    temperature: float
    temperaturePerceived: float
    humidity: int
    pressure: int
    windSpeed: float
    indexUV: float
    description: str

@app.post("/generate-pdf")
def generate_pdf(data: List[WeatherSnapshot] = Body(...)):
    pdf = FPDF()
    pdf.add_page()
    pdf.set_font("Arial", size=12)

    pdf.cell(200, 10, txt="Raport pogodowy", ln=True, align='C')
    pdf.ln(10)

    for i, item in enumerate(data, 1):
        pdf.multi_cell(0, 10,
            f"{i}. Data: {item.recordedAt} | Temp: {item.temperature}℃ "
            f"(odczuwalna {item.temperaturePerceived}℃), "
            f"Wilg: {item.humidity}%, Ciśn: {item.pressure}hPa, "
            f"Wiatr: {item.windSpeed}m/s, UV: {item.indexUV}, "
            f"Opis: {item.description}"
        )
        pdf.ln(2)

    pdf.output("raport_pogodowy.pdf")
    return {"status": "OK", "message": "Plik PDF wygenerowany jako 'raport_pogodowy.pdf'"}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=5000, reload=True)
