package com.minicolestore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.minicolestore.repository.ContactoRepository;
import com.minicolestore.repository.ProductoRepository;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.AuthService;
import com.minicolestore.service.CambiarRoleService;
import com.minicolestore.service.ChangePasswordService;
import com.minicolestore.service.ContactoService;
import com.minicolestore.service.MapAuthService;
import com.minicolestore.service.PasswordValidationService;
import com.minicolestore.service.UserService;
import com.minicolestore.service.Impl.EnvioEmail;
import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

@CrossOrigin(origins = "*")
@SpringBootApplication
@Controller
public class MiniColeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniColeStoreApplication.class, args);
	}

	@Autowired
	@Qualifier("contactoservice")
	private ContactoService contactoService;

	@Autowired
	UserService userService;

	@Autowired
	AuthService authService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ContactoRepository contactoRepository;

	@Autowired
	CambiarRoleService cambiarRoleService;

	@Autowired
	MapAuthService mapAuthService;

	@Autowired
	ProductoRepository productoRepo;

	@Autowired
	ChangePasswordService changePasswordService;

	@Autowired
	PasswordValidationService passwordValidationService;

	@Autowired
	EnvioEmail envioEmail;

	// endpoint mercadopago

	/*
	 * id : 1053565489 nickname : TESTSRPOSNGD password : qatest3272 site_status :
	 * active email : test_user_8115892@testuser.com
	 */

	/*
	 * id : 1053575206 nickname : TEST5XQYPURL password : qatest5461 site_status :
	 * active email : test_user_88365347@testuser.com
	 */

	@PostMapping("/createAndRedirect")
	public String createAndRedirect(HttpServletRequest request) throws MPException {

		String id = request.getParameter("contId");

		long contid = Integer.parseInt(id);

		Producto producto = productoRepo.findById(contid).get();

		MercadoPago.SDK.setAccessToken("TEST-2736337625800802-010900-29bac842d6ed2d46abf62cbe0ef7ac6f-1053565489");
		// Crea un objeto de preferencia
		Preference preference = new Preference();
		// crea un objeto para proporcionar las direcciones que retornan el estado del
		// pago

		BackUrls backUrls = new BackUrls();

		preference.setBackUrls(backUrls.setFailure("http://localhost:8080/failure")
				.setPending("http://localhost:8080/pending").setSuccess("http://localhost:8080/success"));
		// Crea un ítem en la preferencia
		Item item = new Item();
		item.setTitle(producto.getNombre()).setQuantity(1).setUnitPrice(producto.getPrecio())
				.setDescription(producto.getDescripcion());
		preference.appendItem(item);
		var result = preference.save();

		System.out.println(result.getSandboxInitPoint());

		return "redirect:" + result.getSandboxInitPoint();
	}

	@GetMapping("/failure")
	public String failure(HttpServletRequest request) throws MPException {
		String collectionId = request.getParameter("collection_id");

		var payment = com.mercadopago.resources.Payment.findById(collectionId);

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

		return "fallo";
	}

	@GetMapping("/pending")
	public String pending(HttpServletRequest request) throws MPException {
		String collectionId = request.getParameter("collection_id");

		var payment = com.mercadopago.resources.Payment.findById(collectionId);

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

		return "pendiente";
	}

	@GetMapping("/success")
	public String success(HttpServletRequest request, Model modelo) throws MPException {

		String collectionId = request.getParameter("collection_id");

		var payment = com.mercadopago.resources.Payment.findById(collectionId);

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

		return "aprobado";
	}

	// ------------------

	@RequestMapping("/")
	public String index() {

		return "inicio";
	}

	@GetMapping("/crearProducto")
	public String crearProducto(Model modelo) {

		modelo.addAttribute("producto", new Producto());

		return "crearProducto";
	}

	@PostMapping("/crearProducto")
	public String agregarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, Model modelo,
			@RequestParam("image") MultipartFile image) throws IOException {

		if (result.hasErrors()) {

			modelo.addAttribute("producto", producto);
			return "crearProducto";
		} else {
			if (!image.isEmpty()) {

				try {

					producto.setNombreimg(image.getOriginalFilename());

					// Path directorioImg =
					// Paths.get("src//main//resources//static/img/imgproductos");

					// String rutaAds = directorioImg.toFile().getAbsolutePath();
					String rutaAds = "C://Producto//recursos";
					// Get the file and save it somewhere
					byte[] bytes = image.getBytes();

					Path path = Paths.get(rutaAds + "//" + image.getOriginalFilename());

					Files.write(path, bytes);

					productoRepo.save(producto);

					modelo.addAttribute("exito", "El producto se ha agregado con exito");

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				modelo.addAttribute("error", "Selecciona una imagen");
			}

		}

		return "crearProducto";
	}

	@GetMapping("/changePassword")
	public String changePassword() {

		return "changePassword";
	}

	@PostMapping("/changePassword")
	public String processForm(HttpServletRequest request, Model model) {

		String email = request.getParameter("email");

		try {

			changePasswordService.changePassword(email);
			model.addAttribute("successMessage", "Se ha enviado un email a tu correo electronico");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "changePassword";
	}

	@GetMapping("/catalogo")
	public String catalogo(Model model) {

		model.addAttribute("products", productoRepo.findAll());

		return "catalogo";
	}

	@RequestMapping("/registroExitoso")
	public String registroExitoso() {
		return "registroExitoso";
	}

	@RequestMapping("/iniciarSesion")
	public String iniciarSesion() {

		return "IniciarSesion";
	}

	@GetMapping("/verificarEmail")
	public String verificarEmail(HttpServletRequest request, Model model) {

		String code = request.getParameter("code");
		String id = request.getParameter("id");

		if (id == null & code == null) {

		} else {

			long userId = Integer.parseInt(id);

			User user = userRepository.findById(userId).get();

			if (code.equals(user.getCodigoVerificacion())) {

				user.setEnabled(true);
				user.setConfpassword("111");

				userRepository.save(user);

				model.addAttribute("valido", true);
			} else {
				model.addAttribute("valido", false);
			}

		}

		return "verificarEmail";
	}

	@GetMapping("/changePasswordCheck")
	public String changePasswordCheck(HttpServletRequest request, Model model) {

		String code = request.getParameter("code");
		String id = request.getParameter("id");
		String error = request.getParameter("error");

		if (!(error == null)) {

			if (error.equals("true")) {
				model.addAttribute("error",
						"No debe contener espacios en blanco y la contraseña debe contener minimo 8 caracteres que contenga al menos 1.minuscula 1.Mayuscula 1.caracter especial");

			}
			if (error.equals("dontMatch")) {
				model.addAttribute("error","las contraseñas no coinciden");

			}

		}

		if (id == null & code == null) {

		} else {

			long userId = Integer.parseInt(id);

			User user = userRepository.findById(userId).get();

			if (code.equals(user.getCodigoVerificacion())) {
				model.addAttribute("valido", true);
				model.addAttribute("user", user);
				model.addAttribute("passwordChange", new PasswordChange());
			} else {
				model.addAttribute("valido", false);
			}

		}

		return "changePasswordPage";
	}

	@PostMapping("/changePasswordCheck")
	public String changePasswordProcess(@Valid @ModelAttribute PasswordChange passwordChange, BindingResult validation,
			Model model) {

		System.out.println(passwordChange.toString());

		User user = userRepository.findById(passwordChange.getId()).get();

		if (validation.hasErrors()) {

			return "redirect:/changePasswordCheck?id=" + user.getId() + "&code=" + user.getCodigoVerificacion()
					+ "&error=true";

		}

		try {
			passwordValidationService.passwordValidation(passwordChange);

			return "validation";
		} catch (Exception e) {
		
			model.addAttribute("passwordError", e.getMessage());

		}

		return "redirect:/changePasswordCheck?id=" + user.getId() + "&code=" + user.getCodigoVerificacion()+ "&error=dontMatch";
	}

	@RequestMapping("/sobreNosotros")
	public String sobreNosotros() {

		return "sobreNosotros";
	}

	@RequestMapping("/user")

	public String user(ModelMap modelo) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		modelo.addAttribute("admin", authService.getAuth(auth));

		return "user";
	}

	@RequestMapping("/contacto")
	public String contacto(Model modelo) {

		modelo.addAttribute("contacto", new Contacto());

		return "contacto";
	}

	@PostMapping("/procesarContacto")
	public String contacto(@Valid @ModelAttribute Contacto contacto, BindingResult validation, Model model) {

		if (validation.hasErrors()) {

			model.addAttribute("contacto", contacto);
			return "contacto";

		}
		contactoService.addContacto(contacto);

		model.addAttribute("messageSuccess", "Se ha enviado con éxito");

		return "contacto";
	}

	@RequestMapping("/comentarios")
	public ModelAndView comentarios() {

		ModelAndView mav = new ModelAndView("procesarFormulario");
		mav.addObject("contacto", contactoService.ListAllContacto());
		return mav;
	}

	@RequestMapping("/productosAgregados")
	public ModelAndView productosAgregados() {

		ModelAndView mav = new ModelAndView("productosAgregados");
		mav.addObject("productos", productoRepo.findAll());
		return mav;
	}

	@RequestMapping("/usuariosregistrados")
	public ModelAndView usuariosRegistrados() {

		ModelAndView mav = new ModelAndView("UsuariosRegistrados");

		mav.addObject("usuarios", mapAuthService.getUser());

		return mav;
	}

	@RequestMapping("/registro")
	public String registro(Model modelo) {

		modelo.addAttribute("user", new User());

		return "registro";
	}

	@PostMapping("/borrarContacto")
	public String borrarContacto(HttpServletRequest request) {

		String id = request.getParameter("contId");

		int contid = Integer.parseInt(id);

		contactoRepository.deleteById(contid);

		return "redirect:/comentarios";
	}

	@PostMapping("/borrarProducto")
	public String borrarProducto(HttpServletRequest request) {

		String id = request.getParameter("contId");

		long contid = Integer.parseInt(id);

		productoRepo.deleteById(contid);

		return "redirect:/productosAgregados";
	}

	@PostMapping("/cambiarRol")
	public String cambiarRol(HttpServletRequest request) {

		String id = request.getParameter("userId");

		long userid = Integer.parseInt(id);

		cambiarRoleService.cambiarRol(userid);

		return "redirect:/usuariosregistrados";
	}

	@PostMapping("/registro")
	public String procesarRegistro(@Valid @ModelAttribute User user, BindingResult resultadoValidacion,
			ModelMap modelo) {

		if (resultadoValidacion.hasErrors()) {
			modelo.addAttribute("user", user);
			return "registro";
		} else {

			try {
				userService.createUser(user);

				String para = user.getEmail();
				String titulo = "Team MiniColeStore";
				String mensaje = "Hola" + user.getNombre() + " " + user.getApellido()
						+ "Muchas gracias por registrarte en nuestra pagina, ingresa al siguiente link para activar tu cuenta:  http://localhost:8080/verificarEmail?id="
						+ user.getId() + "&code=" + user.getCodigoVerificacion();

				envioEmail.sendEmail(para, titulo, mensaje);
				return "redirect:/registroExitoso";

			} catch (Exception e) {

				modelo.addAttribute("errorMessage", e.getMessage());

			}

			return "registro";
		}

	}

}
