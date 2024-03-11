/**
 This package encompasses classes responsible for the user interface functionality.
 Each class within creates distinct components of the GUI and offers methods to adjust their behavior.
 The UI architecture is divided into several modules:
 The MainFrame, which orchestrates the arrangement of buttons and labels surrounding the chart.
 The chart itself, instantiated within the {@code GUI} class, dynamically visualizes the ongoing benchmark.
 This package relies on Swing for its functionality and will not operate without the Swing Library.
 The {@code DiskWorker} class within this package depends on the {@code GUI} class. DiskWorker transmits real-time data
 for a specific block to the GUI class for plotting on the chart.
 The {@code GUI} class is instantiated within the {@code App} class, establishing a dependency. Furthermore,
 the {@code App} class is closely coupled with both the {@code GUI} class and the {@code MainFrame} class.
 The {@code MainFrame} class is reliant on and instantiated by the {@code GUI} class.
 */

package edu.touro.mco152.bm.ui;