package stock.kdj;

public class MACDLine {

    private Float diff;
    private Float dea;
    private Float macd;
    private Float ema12;
    private Float ema26;
    private Float dSubd;
    private Float weight;

    public MACDLine(Float diff, Float dea, Float macd, Float ema12, Float ema26) {
        this.diff = diff;
        this.dea = dea;
        this.macd = macd;
        this.ema12 = ema12;
        this.ema26 = ema26;
        this.dSubd = this.dea - this.diff;
    }

    public Float getEma12() {
        return ema12;
    }

    public void setEma12(Float ema12) {
        this.ema12 = ema12;
    }

    public Float getEma26() {
        return ema26;
    }

    public void setEma26(Float ema26) {
        this.ema26 = ema26;
    }

    public Float getDiff() {
        return diff;
    }

    public void setDiff(Float diff) {
        this.diff = diff;
    }

    public Float getDea() {
        return dea;
    }

    public void setDea(Float dea) {
        this.dea = dea;
    }

    public Float getMacd() {
        return macd;
    }

    public void setMacd(Float macd) {
        this.macd = macd;
    }

    public Float getdSubd() {
        return dSubd;
    }

    public void setdSubd(Float dSubd) {
        this.dSubd = dSubd;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
