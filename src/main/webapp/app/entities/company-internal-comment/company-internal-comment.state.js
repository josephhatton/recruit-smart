(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('company-internal-comment', {
        parent: 'entity',
        url: '/company-internal-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'CompanyInternalComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/company-internal-comment/company-internal-comments.html',
            controller: 'CompanyInternalCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('company-internal-comment-detail', {
        parent: 'company-internal-comment',
        url: '/company-internal-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'CompanyInternalComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/company-internal-comment/company-internal-comment-detail.html',
            controller: 'CompanyInternalCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'CompanyInternalComment', function ($stateParams, CompanyInternalComment) {
            return CompanyInternalComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'company-internal-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('company-internal-comment-detail.edit', {
        parent: 'company-internal-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-internal-comment/company-internal-comment-dialog.html',
            controller: 'CompanyInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['CompanyInternalComment', function (CompanyInternalComment) {
                return CompanyInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('company-internal-comment.new', {
        parent: 'company-internal-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-internal-comment/company-internal-comment-dialog.html',
            controller: 'CompanyInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('company-internal-comment', null, {reload: 'company-internal-comment'});
          }, function () {
            $state.go('company-internal-comment');
          });
        }]
      })
      .state('company-internal-comment.edit', {
        parent: 'company-internal-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-internal-comment/company-internal-comment-dialog.html',
            controller: 'CompanyInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['CompanyInternalComment', function (CompanyInternalComment) {
                return CompanyInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('company-internal-comment', null, {reload: 'company-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('company-internal-comment.delete', {
        parent: 'company-internal-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-internal-comment/company-internal-comment-delete-dialog.html',
            controller: 'CompanyInternalCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['CompanyInternalComment', function (CompanyInternalComment) {
                return CompanyInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('company-internal-comment', null, {reload: 'company-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
