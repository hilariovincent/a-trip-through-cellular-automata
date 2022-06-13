This project is as the title suggests, A Trip Through Cellular Automata.

There are three parts to this project separated through the different package:
Elementary CA, Conway's Game of Life, and Langton's Loop. If you want more information about
these 3 types of cellular automata, please check their wikipedia pages

To Activate Elementary CA:

1. Go to the Main file
2. Run Main and look at the CLI
3. You will be prompted to either read in file or generate rule
4. If you choose read in file, I have provided the resources file in this repository,
   you can use the txt files under the elementaryCA folder or create your own file similar
   format to the files I have provided
5. If you choose to generate, enter a number from 0 to 255 for the rule number
   and a String that only has ones and zeros for the initial state

To Activate Game of Life

1. Go to GameOfLife/Main
2. Run Main and look at the CLI
3. You will be prompted to read a file or do a manual entry
4. If you choose read in file, I have provided the resources file in this repository,
   you can use the txt files under the gameOfLife folder or create your own file similar
   format to the files I have provided
5. If you choose manual entry, follow the instructions on the CLI

To Activate Langton's Loop

1. Go to LangtonsLoop\Main
2. Run Main and look at the CLI
3. Enter file path to the rule table under resources folder
4. I hardcoded the file path of the config table so you would need to download the resources file
   but you could also alter my code so you can enter the file manually instead

The jdk I used for this is zulu 17.32.13 that includes the java fx
