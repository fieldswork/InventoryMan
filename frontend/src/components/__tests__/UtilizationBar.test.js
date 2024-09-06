import React from "react";
import { render } from "@testing-library/react";
import { act } from 'react';
import UtilizationBar from "../UtilizationBar";

describe("UtilizationBar", () => { // UtilizationBar is a progress bar component we're using to show the capacity of a warehouse
    it("should render a progress bar", () => { // Checks if the progress bar is rendered
        let container;
        act(() => { // Giving the bar some capacity and used capacity
            ({ container } = render(
                <UtilizationBar usedCapacity={50} capacity={100} />
            ));
        });
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toBeInTheDocument(); // The progress bar should be rendered assertion
    });
    
    it("should render a blue progress bar when under 75% capacity", () => { // Checking case when capacity is under 75%
        let container;
        act(() => {
            ({ container } = render(
                <UtilizationBar usedCapacity={50} capacity={100} />
            ));
        });
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-primary"); // Under 75% capacity should be blue
    });

    it("should render a yellow progress bar when between 75-100% capacity", () => { // Checking case when capacity is between 75-100%
        let container;
        act(() => {
            ({ container } = render(
                <UtilizationBar usedCapacity={75} capacity={100} />
            ));
        });
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-warning"); // Between 75-100% capacity should be yellow
    });

    it("should render a red progress bar when at 100% capacity", () => { // Checking case when capacity is at 100%
        let container;
        act(() => {
            ({ container } = render(
                <UtilizationBar usedCapacity={100} capacity={100} />
            ));
        });
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-danger"); // At 100% capacity should be red
    });
});