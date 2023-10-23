package reversi;

public class ReversiController implements IController {

    IModel model;
    IView view;

    public static void main(String[] args) {
    }

    @Override
    public void initialise(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void startup() {

        for (int i = 0; i < model.getBoardWidth(); i++) {
            for (int j = 0; j < model.getBoardHeight(); j++) {
                model.setBoardContents(i, j, 0);
            }
        }

        model.setBoardContents(3, 3, 1);
        model.setBoardContents(4, 4, 1);
        model.setBoardContents(3, 4, 2);
        model.setBoardContents(4, 3, 2);

        model.setFinished(false);
        model.setPlayer(1);
        view.feedbackToUser(1, "White player - choose where to put your piece");
        view.feedbackToUser(2, "Black player - not your turn");
        view.refreshView();
    }

    @Override
    public void update() {
        boolean p1Play = false;
        boolean p2Play = false;
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (checkValidity(i, j, model.getPlayer())) {
                    p1Play = true;
                } else if (checkValidity(i, j, getPlayer2())) {
                    p2Play = true;
                }
            }
        }

        if(!p1Play) {
            if(p2Play) {
                model.setPlayer(getPlayer2());
            } else {
                model.setFinished(true);
                endGame();
                return;
            }
        }

        if(model.hasFinished()) {
            model.setFinished(false);
        }


        if(model.getPlayer() == 1) {
            view.feedbackToUser(1, "White player - choose where to put your piece");
            view.feedbackToUser(2, "Black player - not your turn");
        }
        else {
            view.feedbackToUser(1, "White player - not your turn");
            view.feedbackToUser(2, "Black player - choose where to put your piece");
        }
        view.refreshView();
    }

    @Override
    public void squareSelected(int player, int x, int y) {
        if (model.hasFinished()) {
            endGame();
            return;
        }

        if (model.getPlayer() != player) {
            view.feedbackToUser(player, "It is not your turn!");
            return;
        }

        boolean isMoveValid = checkValidity(x, y, player);
        if (isMoveValid) {
            flipPieces(x, y, player);
            model.setBoardContents(x, y, player);
            model.setPlayer(getPlayer2());
            update();
        }
    }


    public int getPlayer2() {
        if(model.getPlayer() == 1) {
            return 2;
        }
        else {
            return 1;
        }
    }

    public boolean checkValidity(int x, int y, int player) {
        if (model.getBoardContents(x, y) > 0) {
            return false;
        }

        int total = checkNorth(x, y, player) + checkSouth(x, y, player) +
                checkEast(x, y, player) + checkWest(x, y, player) +
                checkNorthEast(x, y, player) + checkSouthEast(x, y, player) +
                checkSouthWest(x, y, player) + checkNorthWest(x, y, player);

        if(total > -0) {
            return true;
        }
        else {
            return false;
        }
    }


    public void flipPieces(int x, int y, int player) {
        int count;
        count = checkNorth(x, y, player);
        if(count > 0) {
            flipNorth(x, y, player);
        }
        count = checkSouth(x, y, player);
        if(count > 0) {
            flipSouth(x, y, player);
        }
        count = checkEast(x, y, player);
        if(count > 0) {
            flipEast(x, y, player);
        }
        count = checkWest(x, y, player);
        if(count > 0) {
            flipWest(x, y, player);
        }
        count = checkNorthEast(x, y, player);
        if(count > 0) {
            flipNorthEast(x, y, player);
        }
        count = checkNorthWest(x, y, player);
        if(count > 0) {
            flipNorthWest(x, y, player);
        }
        count = checkSouthEast(x, y, player);
        if(count > 0) {
            flipSouthEast(x, y, player);
        }
        count = checkSouthWest(x, y, player);
        if(count > 0) {
            flipSouthWest(x, y, player);
        }
    }

    public int checkNorth(int row, int col, int player) {
        int capturedPieces = 0;
        boolean foundPlayerPiece = false;

        for (int i = col - 1; i >= 0; i--) {
            int boardContent = model.getBoardContents(row, i);
            if (boardContent == 0) {
                break;
            } else if (boardContent == player) {
                foundPlayerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }

        return foundPlayerPiece ? capturedPieces : 0;
    }

    public int checkSouth(int row, int col, int player) {
        int capturedPieces = 0;
        boolean foundPlayerPiece = false;

        for (int i = col + 1; i < model.getBoardHeight(); i++) {
            int boardContent = model.getBoardContents(row, i);
            if (boardContent == 0) {
                break;
            } else if (boardContent == player) {
                foundPlayerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }

        return foundPlayerPiece ? capturedPieces : 0;
    }

    public int checkEast(int row, int col, int player) {
        int capturedPieces = 0;
        boolean foundPlayerPiece = false;


        for (int i = row + 1; i < model.getBoardWidth(); i++) {
            int piece = model.getBoardContents(i, col);


            if (piece == 0) {
                break;
            } else if (piece == player) {
                foundPlayerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }


        return foundPlayerPiece ? capturedPieces : 0;
    }

    public int checkWest(int row, int col, int player) {
        int capturedPieces = 0;
        boolean foundPlayerPiece = false;


        for (int i = row - 1; i >= 0; i--) {
            int piece = model.getBoardContents(i, col);


            if (piece == 0) {
                break;
            } else if (piece == player) {
                foundPlayerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }
        
        return foundPlayerPiece ? capturedPieces : 0;
    }

    public int checkNorthEast(int row, int col, int player) {
        int capturedPieces = 0;
        boolean foundPlayerPiece = false;


        for (int i = row + 1, j = col - 1; i < model.getBoardWidth() && j >= 0; i++, j--) {
            int piece = model.getBoardContents(i, j);


            if (piece == 0) {
                break;
            } else if (piece == player) {
                foundPlayerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }


        return foundPlayerPiece ? capturedPieces : 0;
    }

    public int checkNorthWest(int row, int col, int player) {
        int capturedPieces = 0;
        boolean playerPiece = false;

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            int piece = model.getBoardContents(i, j);
            if (piece == 0) {
                break;
            } else if (piece == player) {
                playerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }

        return playerPiece ? capturedPieces : 0;
    }

    public int checkSouthEast(int row, int col, int player) {
        int capturedPieces = 0;
        boolean playerPiece = false;

        for (int i = row + 1, j = col + 1; i < model.getBoardWidth() && j < model.getBoardHeight(); i++, j++) {
            int boardContents = model.getBoardContents(i, j);
            if (boardContents == 0) {
                break;
            } else if (boardContents == player) {
                playerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }

        return playerPiece ? capturedPieces : 0;
    }

    public int checkSouthWest(int row, int col, int player) {
        int capturedPieces = 0;
        boolean playerPiece = false;

        for (int i = row - 1, j = col + 1; i >= 0 && j < model.getBoardHeight(); i--, j++) {
            int content = model.getBoardContents(i, j);
            if (content == 0) {
                break;
            } else if (content == player) {
                playerPiece = true;
                break;
            } else {
                capturedPieces++;
            }
        }

        return playerPiece ? capturedPieces : 0;
    }




    public void flipNorth(int row, int col, int player) {
        for (int i = col - 1; i >= 0; i--) {
            if (model.getBoardContents(row, i) == player) {
                break;
            }
            else {
                model.setBoardContents(row, i, player);
            }
        }
    }

    public void flipSouth(int row, int col, int player) {
        for (int i = col + 1; i < model.getBoardHeight(); i++) {
            if (model.getBoardContents(row, i) == player) {
                break;
            }
            else {
                model.setBoardContents(row, i, player);
            }
        }
    }

    public void flipEast(int row, int col, int player) {
        for (int i = row + 1; i < model.getBoardWidth(); i++) {
            if (model.getBoardContents(i, col) == player) {
                break;
            }
            else {
                model.setBoardContents(i, col, player);
            }
        }
    }

    public void flipWest(int row, int col, int player) {
        for (int i = row - 1; i >= 0; i--) {
            if (model.getBoardContents(i, col) == player) {
                break;
            }
            else {
                model.setBoardContents(i, col, player);
            }
        }
    }
    public void flipNorthEast(int row, int col, int player) {
        for (int i = row + 1, j = col - 1; i < model.getBoardWidth() && j >= 0; i++,j--){
            if (model.getBoardContents(i, j) == player) {
                break;
            }
            else {
                model.setBoardContents(i, j, player);
            }
        }
    }
    public void flipNorthWest(int row, int col, int player) {
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (model.getBoardContents(i, j) == player) {
                break;
            } else {
                model.setBoardContents(i, j, player);
            }
        }
    }
    public void flipSouthEast(int row, int col, int player) {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        for (int i = row + 1, j = col + 1; i < width && j < height; i++, j++) {
            if (model.getBoardContents(i, j) == player) {
                break;
            } else {
                model.setBoardContents(i, j, player);
            }
        }
    }

    public void flipSouthWest(int row, int col, int player) {
        for (int i = row - 1, j = col + 1; i >=0 && j < model.getBoardHeight(); i--, j++)  {
            if (model.getBoardContents(i, j) == player) {
                break;
            }
            else {
                model.setBoardContents(i, j, player);
            }
        }
    }




    @Override
    public void doAutomatedMove(int player) {
        int bestX = -1;
        int bestY = -1;
        int highest = 0;

        for (int x = 0; x < model.getBoardWidth(); x++) {
            for (int y = 0; y < model.getBoardHeight(); y++) {
                if (checkValidity(x, y, player)) {
                    int count = 0;
                    for (int i = 0; i < 8; i++) {
                        switch (i) {
                            case 0:
                                count += checkNorth(x, y, player);
                                break;
                            case 1:
                                count += checkSouth(x, y, player);
                                break;
                            case 2:
                                count += checkEast(x, y, player);
                                break;
                            case 3:
                                count += checkWest(x, y, player);
                                break;
                            case 4:
                                count += checkNorthEast(x, y, player);
                                break;
                            case 5:
                                count += checkNorthWest(x, y, player);
                                break;
                            case 6:
                                count += checkSouthEast(x, y, player);
                                break;
                            case 7:
                                count += checkSouthWest(x, y, player);
                                break;
                        }
                    }
                    if (count > highest) {
                        highest = count;
                        bestX = x;
                        bestY = y;
                    }
                }
            }
        }
        if (bestX == -1 && bestY == -1) {
            return;
        } else {
            squareSelected(player, bestX, bestY);
        }
    }

    private void endGame() {
        int p1Pieces = 0;
        int p2Pieces = 0;
        String result = null;

        for (int i = 0 ; i < model.getBoardWidth() ; i++) {
            for (int j = 0 ; j < model.getBoardHeight() ; j++) {
                int piece = model.getBoardContents(i, j);
                if (piece == 1) {
                    p1Pieces++;
                } else if (piece == 2) {
                    p2Pieces++;
                }
            }
        }

        if (p1Pieces > p2Pieces) {
            result = "White won. White " + p1Pieces + " to Black " + p2Pieces + ". Reset the Game to replay.";
        } else if (p2Pieces > p1Pieces) {
            result = "Black Won. Black " + p2Pieces + " to White " + p1Pieces + ". Reset the Game to replay.";
        } else {
            result = "Draw. Both players ended with " + p1Pieces + " pieces. Reset Game to replay.";
        }

        view.feedbackToUser(1, result);
        view.feedbackToUser(2, result);
    }



}