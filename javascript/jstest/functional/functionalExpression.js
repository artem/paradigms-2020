"use strict";

const cnst = value => () => value;
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const variable = name => {
    return (x, y, z) => {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    }
};

const parse = input => input.trim() === "x" ? variable("x") : cnst(+input);

const negate = f1 => (x, y, z) => -f1(x, y, z);

const operation = op => (...ops) => (x, y, z) => op(...(ops.map(value => value(x, y, z))));

const add = operation((v1, v2) => v1 + v2);
const subtract = operation((v1, v2) => v1 - v2);
const multiply = operation((v1, v2) => v1 * v2);
const divide = operation((v1, v2) => v1 / v2);

const med3 = operation((v1, v2, v3) => Math.max(Math.min(v1, v2), Math.min(v1, v3), Math.min(v2, v3)));
const avg5 = operation((v1, v2, v3, v4, v5) => (v1 + v2 + v3 + v4 + v5) / 5);
