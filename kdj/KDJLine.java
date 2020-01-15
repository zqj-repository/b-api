package stock.kdj;

public class KDJLine {

    private Float k;
    private Float d;
    private Float j;
    private Float rsv;

    public KDJLine(Float k, Float d, Float j, Float rsv) {
        this.k = k;
        this.d = d;
        this.j = j;
        this.rsv = rsv;
    }

    public Float getK() {
        return k;
    }

    public void setK(Float k) {
        this.k = k;
    }

    public Float getD() {
        return d;
    }

    public void setD(Float d) {
        this.d = d;
    }

    public Float getJ() {
        return j;
    }

    public void setJ(Float j) {
        this.j = j;
    }

    public Float getRsv() {
        return rsv;
    }

    public void setRsv(Float rsv) {
        this.rsv = rsv;
    }

    @Override
    public String toString() {
        return "KDJLine{" +
                "k=" + k +
                ", d=" + d +
                ", j=" + j +
                '}';
    }
}
