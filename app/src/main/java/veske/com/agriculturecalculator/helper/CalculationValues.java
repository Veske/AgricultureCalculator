package veske.com.agriculturecalculator.helper;

import android.content.res.Resources;

import veske.com.agriculturecalculator.R;

public class CalculationValues {

    private float[] N;
    private float[] P;
    private float[] K;
    private float[] Mg;
    private float[] S;
    private float[] X;

    CalculationValues(Resources resources) {
        String[] n = resources.getStringArray(R.array.N);
        String[] p = resources.getStringArray(R.array.P);
        String[] k = resources.getStringArray(R.array.K);
        String[] mg = resources.getStringArray(R.array.Mg);
        String[] s = resources.getStringArray(R.array.S);
        String[] x = resources.getStringArray(R.array.harvestValues);

        N = new float[n.length];
        P = new float[p.length];
        K = new float[k.length];
        Mg = new float[mg.length];
        S = new float[s.length];
        X = new float[x.length];

        for (int i = 0; i < x.length; i++) {
            if ((i < n.length)) {
                N[i] = Float.parseFloat(n[i]);
                P[i] = Float.parseFloat(p[i]);
                K[i] = Float.parseFloat(k[i]);
                Mg[i] = Float.parseFloat(mg[i]);
                S[i] = Float.parseFloat(s[i]);
            }
            X[i] = Float.parseFloat(x[i].replace(" tn/ha", ""));
        }
    }

    public float[] getN() {
        return N;
    }

    public float[] getP() {
        return P;
    }

    public float[] getK() {
        return K;
    }

    public float[] getMg() {
        return Mg;
    }

    public float[] getS() {
        return S;
    }

    public float[] getX() {
        return X;
    }
}
