package nl.cge.jakartaee8.transakties.boundary;

import nl.cge.jakartaee8.transakties.control.HandleFileUploadController;
import nl.cge.jakartaee8.transakties.control.InvalidTransaktiebestandException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Inject
    private HandleFileUploadController controller;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        try {
            controller.execute(filePart.getInputStream());
            response.sendRedirect("transakties.html");
        } catch (InvalidTransaktiebestandException e) {
            response.getWriter().println(e.getMessage());
        }
    }
}
