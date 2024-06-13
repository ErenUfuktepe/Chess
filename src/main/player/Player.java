package main.player;

import main.factories.PieceFactory;
import main.moves.Position;
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Rook;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private Map<String, Piece> pieceMap;
    private List<Piece> pieces = new ArrayList<>();
    private static final PieceFactory pieceFactory = new PieceFactory();

    public Player(Color color) {
        setPieces(pieceFactory.createPiecesForPlayer(color));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Player setPieceMap(Map<String, Piece> pieceMap) {
        this.pieceMap = pieceMap;
        return this;
    }

    public boolean isPlayerPiece(Piece piece) {
        return this.pieces.contains(piece);
    }

    public String movePiece(Piece piece, String newPositionKey) {
        String previousKey = piece.getPosition().getKey();
        piece.movePiece(newPositionKey);
        return previousKey;
    }

    public String takePiece(Piece pieceToMove, Piece pieceToTaken) {
        String previousKey = pieceToMove.getPosition().getKey();
        pieceToMove.takes(pieceToTaken);
        return previousKey;
    }

    public void doCastling(King king, Rook rook) {
        king.doCastling(rook);
    }

    private Position getKingPosition() {
        King king = (King) this.getPieces().parallelStream()
                .filter(piece -> piece.getClass().equals(King.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No king found for the player!"));
        return king.getPosition();
    }

    public List<Position> getPossiblePositions(Piece piece) {
        return piece.getPossibleMoves(pieceMap);
    }

    public List<Position> getAllPossiblePositionsForAllPieces() {
        List<Position> allPositions = new ArrayList<>();
        this.pieces.parallelStream()
                .filter(piece ->  !piece.isTaken())
                .forEach(piece -> allPositions.addAll(piece.getPossibleMoves(this.pieceMap)));
        return allPositions;
    }

    public boolean isChecked(Player opponentPlayer) {
        List<Position> opponentAllPositions = opponentPlayer.getAllPossiblePositionsForAllPieces();
        for (Position opponentPosition : opponentAllPositions) {
            if (opponentPosition.getKey().equals(getKingPosition().getKey())) {
                return true;
            }
        }
        return false;
    }

}
