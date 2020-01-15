package stock.kdj;

public class LowAndHi {

    private Float low;
    private Float hi;

    public LowAndHi(Float low, Float hi) {
        this.low = low;
        this.hi = hi;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getHi() {
        return hi;
    }

    public void setHi(Float hi) {
        this.hi = hi;
    }
}
