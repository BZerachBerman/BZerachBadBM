package edu.touro.mco152.bm;

/**
 * BenchMark is an interface for different kinds of Benchmarking classes, which will decide themselves
 * how they want to run a benchMark and what benchmark to do.
 */
public interface BenchMark {
    boolean runBenchMark(Worker worker);
}
