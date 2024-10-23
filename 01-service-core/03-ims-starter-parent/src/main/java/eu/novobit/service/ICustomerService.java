package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface Customer service.
 */
public interface ICustomerService extends ICrudServiceMethods<Customer>, ICrudImageService<Customer> {

    /**
     * Create customer customer.
     *
     * @param file     the file
     * @param customer the customer
     * @return the customer
     */
    Customer createCustomer(MultipartFile file, Customer customer);

    /**
     * Update customer customer.
     *
     * @param customer the customer
     * @return the customer
     */
    Customer updateCustomer(Customer customer);


    /**
     * Gets names.
     *
     * @return the names
     */
    List<String> getNames();

    /**
     * Update customer status customer.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the customer
     */
    Customer updateStatus(Long id, IEnumBinaryStatus.Types newStatus);

    /**
     * Update customer picture customer.
     *
     * @param id   the id
     * @param file the file
     * @return the customer
     * @throws IOException the io exception
     */
    Customer updateCustomerPicture(Long id, MultipartFile file) throws IOException;
}
