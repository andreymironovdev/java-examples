# Java Memory Model

## CPU Memory model

### Cache

* Visibility: Each core has its own L1 and L2 caches, while the L3 cache, also called the Last Level Cache or LLC, is
  shared among cores.

### Registers

* Visibility: Registers are private to each CPU core and are not shared between cores. Each core has its own set of
  registers, allowing for independent execution and data manipulation.