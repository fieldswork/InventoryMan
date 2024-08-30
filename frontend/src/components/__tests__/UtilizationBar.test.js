import React from "react";
import { render } from "@testing-library/react";
import UtilizationBar from "../UtilizationBar";

describe("UtilizationBar", () => {
    it("should render a progress bar", () => {
        const { container } = render(
        <UtilizationBar usedCapacity={50} capacity={100} />
        );
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toBeInTheDocument();
    });
    
    it("should render a blue progress bar when under 75% capacity", () => {
        const { container } = render(
        <UtilizationBar usedCapacity={50} capacity={100} />
        );
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-primary");
    });

    it("should render a yellow progress bar when between 75-100% capacity", () => {
        const { container } = render(
        <UtilizationBar usedCapacity={75} capacity={100} />
        );
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-warning");
    });

    it("should render a red progress bar when at 100% capacity", () => {
        const { container } = render(
        <UtilizationBar usedCapacity={100} capacity={100} />
        );
        const progressBar = container.querySelector(".progress-bar");
        expect(progressBar).toHaveClass("bg-danger");
    });
});