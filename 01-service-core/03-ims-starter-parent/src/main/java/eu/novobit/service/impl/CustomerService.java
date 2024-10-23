package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.StoreFileException;
import eu.novobit.helper.FileHelper;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Customer;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.CustomerRepository;
import eu.novobit.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Customer service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = CustomerRepository.class)
public class CustomerService extends AbstractCrudImageService<Customer, CustomerRepository> implements ICustomerService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Customer service.
     *
     * @param appProperties the app properties
     */
    public CustomerService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain(Customer.class.getSimpleName())
                .entity(Customer.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RCUST")
                .valueLength(6L)
                .value(1L)
                .build();
    }

    @Override
    public Customer createCustomer(MultipartFile file, Customer customer) {
        if (file != null) {
            try {
                customer.setImagePath(FileHelper.storeMultipartFile(appProperties.getUploadDirectory() + File.separator + "customer",
                        customer.getName(),
                        file, "").toFile().getName());
            } catch (IOException e) {
                throw new StoreFileException(e);
            }
        }
        return this.create(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {

        return this.update(customer);
    }

    @Override
    public List<String> getNames() {
        return repository().findAll().stream().map(customer -> customer.getName()).toList();
    }

    @Override
    public Customer updateStatus(Long id, IEnumBinaryStatus.Types newStatus) {
        ((CustomerRepository) repository()).updateAdminStatusById(id, newStatus);
        return repository().findById(id).orElse(null);
    }


    @Override
    public Customer updateCustomerPicture(Long id, MultipartFile file) throws IOException {
        Customer customer = findById(id);

        if (customer != null) {
            try {
                customer.setImagePath(FileHelper.storeMultipartFile(appProperties.getUploadDirectory() + File.separator + "customer",
                        customer.getName(),
                        file, "").toFile().getName());
            } catch (IOException e) {
                throw new StoreFileException(e);
            }

            return saveOrUpdate(customer);
        }

        return null;
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }
}
